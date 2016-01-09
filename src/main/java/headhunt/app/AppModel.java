package headhunt.app;

import com.orientechnologies.orient.core.exception.OStorageException;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import headhunt.schemas.TwitterUser;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
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
        return (JSONObject) parser.parse(new InputStreamReader(is));
    }

    public static void openDB(String dbType,String dbUrl) {

        //Open connection
        String dbInfo = dbType + ":";
        if(dbType.equals("plocal")){
            //Todo: If installPath is not setted make error obout this...
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
        db.getEntityManager().registerEntityClass(TwitterUser.class);
    }

    public static void seed(){
        System.out.println("SEEDING");

        //Seeding
        TwitterUser user = new TwitterUser();

        user.setUri("http://google.com");
        user.setName("Uros Jarc");
        user.setLink("http://google.com");
        user.setLocation("Ljubljana");
        user.setBio("This is my bio\nand this is new line.");
        user.setAccount("Pro account");
        user.setCreatedTime("Todo...");

        //Todo: Make portrait, websites define schema...
        user.setPortrait("aldfkj");
        user.setWebsites("lasjkdf");
        //----------------------------------------------

        Map<String,Integer> stats = new HashMap<String, Integer>();
        stats.put("stats0", 34);
        stats.put("stats1", 200);
        user.setStatistics(stats);

        user.save();

    }

    public static void closeDB() {
        db.close();
    }
}
