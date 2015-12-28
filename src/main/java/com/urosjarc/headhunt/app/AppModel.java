package com.urosjarc.headhunt.app;

import com.orientechnologies.orient.core.exception.OStorageException;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.urosjarc.headhunt.schemas.TwitterUser;

public class AppModel {

    @Getter
    private static OObjectDatabaseTx db;

    @PostConstruct
    public void init() {
        System.out.println("AppModel.init()");
    }

    public static Object getConfig(String configUrl) throws Exception{
        JSONParser parser = new JSONParser();
        InputStream is = AppModel.class.getResourceAsStream(configUrl);
        JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(is));
        return jsonObject.get(App.getEnv());
    }

    public static void openDB(String dbType,String dbUrl) {
        //Open connection
        String dbInfo = dbType + ":";
        if(dbType.equals("plocal")){
            //Todo: Make splash screen with configuring application for the first time...
            dbInfo = dbInfo + System.getProperty("user.home") + "/" + dbUrl;
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
        user.setPortrait("http://newshour.s3.amazonaws.com/photos/2011/01/05/portrait-walken_slideshow.jpg");

        List<String> websites = new ArrayList<String>();
        websites.add("websites0");
        websites.add("websites1");
        websites.add("websites2");
        user.setWebsites(websites);

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
