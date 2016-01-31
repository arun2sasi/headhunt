package headhunt;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.sun.javafx.application.LauncherImpl;
import headhunt.database.Db;
import headhunt.database.classes.VimeoUser;
import headhunt.database.classes.VimeoUsersScraper;
import headhunt.database.schemas.Schema;
import headhunt.fx.app.AppFx;
import headhunt.fx.preloader.PreloaderFx;
import headhunt.fx.setup.SetupFx;
import headhunt.services.ApiScrape;
import headhunt.services.ScrapeTask;
import javafx.application.Platform;
import lombok.Getter;
import org.docopt.Docopt;
import com.jcabi.manifests.Manifests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

public class Main {

	final public static String ENV = System.getProperty("ENV") == null ? "production" : System.getProperty("ENV");

	@Getter private static String title;
	@Getter private static String version;
	@Getter private static String buildDate;
	@Getter private static String dbType;
	@Getter private static String dbUrl;
	@Getter private static String installPath;
	@Getter private static String description;

	private static void setProperties(){

		Preferences prefs = Preferences.userNodeForPackage(Main.class);

		try {
			Properties props = new Properties();
			props.load( Main.class.getResourceAsStream("/config.properties") );

			title = props.getProperty("title");
			version = props.getProperty("version");
			description = props.getProperty("description");
			buildDate = props.getProperty("build.date");
			dbType = props.getProperty(ENV + ".db.type");
			dbUrl = getInstallPath() + "/" + "database"; //Todo Make this secured!
			installPath = prefs.get("installPath",null);

		} catch (IOException e) {
			e.printStackTrace();
			//Todo Make this better!
			System.exit(1);
		}

	}

	public static void main(String[] args) {

		setProperties();

		String appBinTitle = title.toLowerCase();

        Map<String, Object> opts = new Docopt(
            "Description:\n"
            + String.format("  %s\n",description)
            + "\n"
            + "Usage:\n"
            + String.format("  %s\n", appBinTitle)
			+ String.format("  %s (-h | --help)\n",appBinTitle)
            + String.format("  %s (-v | --version)\n",appBinTitle)
            + "\n"
            + "Options:\n"
            + "  -v --version  Show version.\n"
            + "  -h --help     Show this screen.\n"
            + "\n"
        ).withVersion(title + " " + version).parse(args);

        if (getInstallPath() != null) {
            LauncherImpl.launchApplication(AppFx.class, PreloaderFx.class, args);
        } else {
            LauncherImpl.launchApplication(SetupFx.class,args);
        }
    }

	public static void setInstallPath(String path){
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		prefs.put("installPath",path);
	}

	public static List<ScrapeTask> initScraping() {

		//Get all scrapers
		List<VimeoUsersScraper> vimeoUsersScrapers = (List<VimeoUsersScraper>) Schema.query("select * from VimeoUsersScraper");

		//Filling scraper informations
		List<ScrapeTask> scrapersTasks = new ArrayList<>();
		for (VimeoUsersScraper vimeoUsersScraper : vimeoUsersScrapers) {

			ScrapeTask scrapeTask = ApiScrape.vimeoUsers(vimeoUsersScraper);

			scrapeTask.setOnScrapeSuccess(param -> {
				ODatabaseRecordThreadLocal.INSTANCE.set(Db.getConnection().getUnderlying());
				System.out.println("SUCCESS -> " + param);
				VimeoUser.insertOrUpdateAllReqUsers(param);
				Platform.runLater(VimeoUser::updateCountProperty);
				return null;
			});

			scrapeTask.setOnScrapeFail(param -> {
				//Todo: Make logging.
				System.out.println("FAIL -> " + param);
				return null;
			});

			scrapeTask.setOnScrapeError(param -> {
				//Todo: Make logging.
				System.out.println("ERROR -> " + param);
				return null;
			});

			scrapersTasks.add(scrapeTask);

			//Todo: Save threads in model...
			new Thread(scrapeTask).start();
		}

		return scrapersTasks;
	}

}
