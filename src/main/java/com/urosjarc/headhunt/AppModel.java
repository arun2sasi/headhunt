package com.urosjarc.headhunt;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        user.setName("Antoni");
        user.setSurname("Gaudi");
        db.save(user);

        ArrayList<TwitterUser> result = db.query( new OSQLSynchQuery<TwitterUser>("select * from TwitterUser"));
        System.out.println(result.get(0).getName());

    }

    public static void closeDB() {
        db.close();
    }
}
