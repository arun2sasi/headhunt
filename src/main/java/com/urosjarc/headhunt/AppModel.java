package com.urosjarc.headhunt;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.urosjarc.headhunt.schemas.TwitterUser;

public class AppModel {

    private static OObjectDatabaseTx db;

    @PostConstruct
    public void init() {
        System.out.println("AppModel.init()");
    }

    public static Object getConfig(String configUrl) throws Exception{
        JSONParser parser = new JSONParser();
        InputStream is = AppModel.class.getResourceAsStream(configUrl);
        JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(is));
        return jsonObject.get(System.getProperty("ENV"));
    }

    public static void openDB(String databaseUrl) {
        //Open connection
        db = new OObjectDatabaseTx("memory:headhunt").create();

        //Register tables
        db.getEntityManager().registerEntityClass(TwitterUser.class);

        //Seeding
        TwitterUser user = db.newInstance(TwitterUser.class);
        user.setUri("http://google.com");
        user.setName("Uros Jarc");
        user.setLink("http://google.com/urosjarc");
        user.setLocation("Ljubljana");
        user.setBio("This is my bio\nand this is new line.");
        user.setAccount("Pro account");

        List<String> portraits = new ArrayList<String>();
        portraits.add("portraits0");
        portraits.add("portraits1");
        portraits.add("portraits2");
        user.setPortraits(portraits);

        List<String> websites = new ArrayList<String>();
        websites.add("websites0");
        websites.add("websites1");
        websites.add("websites2");
        user.setWebsites(portraits);

        Map<String,Integer> stats = new HashMap<String, Integer>();
        stats.put("stats0", 34);
        stats.put("stats1", 200);
        user.setStatistics(stats);

        db.save(user);

        ArrayList<TwitterUser> result = db.query( new OSQLSynchQuery<TwitterUser>("select * from TwitterUser"));

    }

    public static void closeDB() {
        db.close();
    }
}
