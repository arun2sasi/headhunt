package headhunt.app;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.tool.ODatabaseExport;
import com.orientechnologies.orient.core.db.tool.ODatabaseExportException;
import com.orientechnologies.orient.core.db.tool.ODatabaseImport;
import com.orientechnologies.orient.core.exception.OStorageException;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import headhunt.schemas.Schema;
import headhunt.schemas.classes.VimeoUser;
import headhunt.schemas.classes.VimeoUsersScraper;
import headhunt.schemas.records.Portrait;
import headhunt.schemas.records.Website;
import headhunt.services.ApiScrape;
import headhunt.services.ScrapeTask;
import javafx.application.Platform;
import javafx.util.Callback;
import lombok.Getter;
import org.json.simple.parser.JSONParser;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class AppModel {

	@Getter
	private static OObjectDatabaseTx db;

	private static Preferences prefs = Preferences.userNodeForPackage(AppModel.class);

	@PostConstruct
	public void init() {
		System.out.println("AppModel.init()");
	}

	public static String getPrefsInstallPath(){
		return prefs.get("installPath",null);
	}

	public static Object getConfig(String env) throws Exception {
		JSONParser parser = new JSONParser();
		InputStream is = AppModel.class.getResourceAsStream("/env/" + env + ".json");
		return parser.parse(new InputStreamReader(is));
	}

	public static List<ScrapeTask> initScraping() {

		//Get all scrapers
		List<VimeoUsersScraper> vimeoUsersScrapers = (List<VimeoUsersScraper>) Schema.query("select * from VimeoUsersScraper");

		//Filling scraper informations
		List<ScrapeTask> scrapersTasks = new ArrayList<>();
		for (VimeoUsersScraper vimeoUsersScraper : vimeoUsersScrapers) {

			ScrapeTask scrapeTask = ApiScrape.vimeoUsers(vimeoUsersScraper);

			scrapeTask.setOnScrapeSuccess(param -> {
				ODatabaseRecordThreadLocal.INSTANCE.set(AppModel.getDb().getUnderlying());
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

	public static void exportDB(String path, Callback log) {
		try {
			ODatabaseExport export = new ODatabaseExport(db.getUnderlying(), path, iText -> {
				log.call(iText);
			});
			export.exportDatabase();
			export.close();
		} catch (ODatabaseExportException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void importDB(String path, Callback log) {
		try {
			ODatabaseImport importDb = new ODatabaseImport(db.getUnderlying(), path, iText -> {
				log.call(iText);
			});
			importDb.importDatabase();
			importDb.close();
		} catch (ODatabaseExportException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void openDB(String dbType, String dbUrl) {

		//Open connection
		String dbInfo = dbType + ":";
		if (dbType.equals("plocal")) {
			dbInfo = dbInfo + AppModel.getPrefsInstallPath() + "/" + dbUrl;
		} else {
			dbInfo = dbInfo + dbUrl;
		}

		System.out.println(dbInfo);
		try {
			db = new OObjectDatabaseTx(dbInfo).open("admin", "admin");
		} catch (OStorageException e) {
			db = new OObjectDatabaseTx(dbInfo).create();
		}

		//Register tables
		db.getEntityManager().registerEntityClass(Website.class);
		db.getEntityManager().registerEntityClass(Portrait.class);
		db.getEntityManager().registerEntityClass(VimeoUser.class);
		db.getEntityManager().registerEntityClass(VimeoUsersScraper.class);
	}

	public static void seed() {
		System.out.println("SEEDING");

		//Seeding
		VimeoUser user = new VimeoUser();
		user.setUri("http://google.com");
		user.setName("Uros Jarc");
		user.setLink("http://google.com");
		user.setLocation("Ljubljana");
		user.setBio("This is my bio\nand this is new line.");
		user.setAccount("Pro account");
		user.setCreatedTime("Todo...");
		user.addPortrait(new Portrait("http://www.accentblinds.ca/wp-content/uploads/2015/06/ncEEjypai.gif", 12, 12));
		user.addWebsite(new Website("alkjfd", "lsjdf", "alsjdf"));
		user.addStat("test", 21);
		user.addStat("test1", 21);
		user.save();

		VimeoUsersScraper scraper0 = new VimeoUsersScraper("Scrape0 vimeo", "96f56eff59f76a764196f8a3a1f9e9d2", "b");
//		VimeoUsersScraper scraper1 = new VimeoUsersScraper("Scrape1 vimeo","96f56eff59f76a764196f8a3a1f9e9d2","a");
//		VimeoUsersScraper scraper2 = new VimeoUsersScraper("Scrape2 vimeo","96f56eff59f76a764196f8a3a1f9e9d2","a");
//		VimeoUsersScraper scraper3 = new VimeoUsersScraper("Scrape3 vimeo","96f56eff59f76a764196f8a3a1f9e9d2","a");
//		VimeoUsersScraper scraper4 = new VimeoUsersScraper("Scrape4 vimeo","96f56eff59f76a764196f8a3a1f9e9d2","a");
//		VimeoUsersScraper scraper5 = new VimeoUsersScraper("Scrape5 vimeo","96f56eff59f76a764196f8a3a1f9e9d2","a");
		scraper0.save();
//		scraper1.save();
//		scraper2.save();
//		scraper3.save();
//		scraper4.save();
//		scraper5.save();
		//--------

	}

	public static void closeDB() {
		db.close();
	}
}
