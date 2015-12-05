package com.urosjarc.headhunt;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AppModel {

    private static Connection dbConnection;

    @PostConstruct
    public void init() throws SQLException {
        System.out.println("AppModel.init()");
    }

    public static Object getConfig(String configUrl) throws Exception{
        JSONParser parser = new JSONParser();
        InputStream is = AppModel.class.getResourceAsStream(configUrl);
        JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(is));
        return jsonObject.get(System.getProperty("ENV"));
    }

    public static void openDB(String databaseUrl) throws SQLException {
        dbConnection = DriverManager.getConnection(databaseUrl);
    }

    public static void closeDB() throws SQLException {
        dbConnection.close();
    }
}
