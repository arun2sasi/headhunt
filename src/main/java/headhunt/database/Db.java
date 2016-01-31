package headhunt.database;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.tool.ODatabaseExport;
import com.orientechnologies.orient.core.db.tool.ODatabaseExportException;
import com.orientechnologies.orient.core.db.tool.ODatabaseImport;
import com.orientechnologies.orient.core.exception.OStorageException;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import headhunt.database.classes.VimeoUser;
import headhunt.database.classes.VimeoUsersScraper;
import headhunt.database.records.Portrait;
import headhunt.database.records.Website;
import headhunt.database.schemas.Schema;
import headhunt.services.ApiScrape;
import headhunt.services.ScrapeTask;
import javafx.application.Platform;
import javafx.util.Callback;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Db {

	@Getter
	private static OObjectDatabaseTx connection;

	public static void exportDB(String path, Callback log) {
		try {
			ODatabaseExport export = new ODatabaseExport(connection.getUnderlying(), path, iText -> {
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
			ODatabaseImport importDb = new ODatabaseImport(connection.getUnderlying(), path, iText -> {
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
		String dbInfo = dbType + ":" + dbUrl;

		System.out.println(dbInfo);
		try {
			connection = new OObjectDatabaseTx(dbInfo).open("admin", "admin");
		} catch (OStorageException e) {
			connection = new OObjectDatabaseTx(dbInfo).create();
		}

		//Register tables
		connection.getEntityManager().registerEntityClass(Website.class);
		connection.getEntityManager().registerEntityClass(Portrait.class);
		connection.getEntityManager().registerEntityClass(VimeoUser.class);
		connection.getEntityManager().registerEntityClass(VimeoUsersScraper.class);
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
		connection.close();
	}
}
