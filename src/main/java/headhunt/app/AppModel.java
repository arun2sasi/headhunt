package headhunt.app;

import com.orientechnologies.orient.core.exception.OStorageException;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import headhunt.schemas.Schema;
import headhunt.schemas.classes.VimeoUsersScraper;
import headhunt.schemas.classes.VimeoUser;
import headhunt.schemas.records.Portrait;
import headhunt.schemas.records.Website;
import headhunt.services.ApiScrape;
import headhunt.services.ScrapeTask;
import lombok.Getter;
import org.json.simple.parser.JSONParser;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class AppModel {

    @Getter
    private static OObjectDatabaseTx db;

    @Getter private static Preferences prefs = Preferences.userNodeForPackage(AppModel.class);

    @PostConstruct
    public void init() {
        System.out.println("AppModel.init()");
    }

    public static Object getConfig(String env) throws Exception{
        JSONParser parser = new JSONParser();
        InputStream is = AppModel.class.getResourceAsStream("/env/"+env+".json");
        return parser.parse(new InputStreamReader(is));
    }

	public List<ScrapeTask> initScraping(){

		//Get all scrapers
		List<VimeoUsersScraper> vimeoScrapers = (List<VimeoUsersScraper>) Schema.query("select * from VimeoUsersScraper");

		//Filling scraper informations
		List<ScrapeTask> scrapersTasks = new ArrayList<>();
		for(VimeoUsersScraper scraper: vimeoScrapers){

			ScrapeTask scrapeTask = ApiScrape.vimeoUsers(
				scraper.getName(),
				scraper.getToken(),
				scraper.getQuery(),
				scraper.getPage()
			);

			scrapeTask.setOnScrapeSuccess(param -> {
				//Todo: Make contract between scrape task response and database schema.
				System.out.println("SUCCESS -> " + param);
				VimeoUser.insertOrUpdateAllReqUsers(param);
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

    public static void openDB(String dbType,String dbUrl) {

        //Open connection
        String dbInfo = dbType + ":";
        if(dbType.equals("plocal")){
            dbInfo = dbInfo + prefs.get("installPath",null) + "/" + dbUrl;
        } else {
            dbInfo = dbInfo + dbUrl;
        }

        System.out.println(dbInfo);
        try{
            db = new OObjectDatabaseTx(dbInfo).open("admin","admin");
        } catch (OStorageException e){
            db = new OObjectDatabaseTx(dbInfo).create();
        }

        //Register tables
        db.getEntityManager().registerEntityClass(Website.class);
        db.getEntityManager().registerEntityClass(Portrait.class);
        db.getEntityManager().registerEntityClass(VimeoUser.class);
		db.getEntityManager().registerEntityClass(VimeoUsersScraper.class);
    }

    public static void seed(){
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
            user.addPortrait(new Portrait("http://www.accentblinds.ca/wp-content/uploads/2015/06/ncEEjypai.gif",12,12));
            user.addWebsite(new Website("alkjfd","lsjdf","alsjdf"));
            user.addStat("test",21);
            user.addStat("test1",21);
        user.save();

		VimeoUsersScraper scraper = new VimeoUsersScraper("Scrape vimeo","96f56eff59f76a764196f8a3a1f9e9d2","a");
		scraper.save();
		//--------

    }

    public static void closeDB() {
        db.close();
    }
}
