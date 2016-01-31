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
import org.docopt.Docopt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		String appBinTitle = Config.PACKAGE.NAME.toLowerCase();

		Map<String, Object> opts = new Docopt(
            "Description:\n"
            + String.format("  %s\n", Config.PACKAGE.DESCRIPTION)
            + "\n"
            + "Usage:\n"
            + String.format("  %s\n", appBinTitle)
            + String.format("  %s (-h | --help)\n", appBinTitle)
            + String.format("  %s (-v | --version)\n", appBinTitle)
            + "\n"
            + "Options:\n"
            + "  -v --version  Show version.\n"
            + "  -h --help     Show this screen.\n"
            + "\n"
		).withVersion(Config.PACKAGE.NAME + " " + Config.PACKAGE.VERSION).parse(args);

		if (Config.getInstallPath() != null) {
			LauncherImpl.launchApplication(AppFx.class, PreloaderFx.class, args);
		} else {
			LauncherImpl.launchApplication(SetupFx.class, args);
		}
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
