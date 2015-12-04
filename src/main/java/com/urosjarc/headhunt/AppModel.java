package com.urosjarc.headhunt;

import com.urosjarc.headhunt.schema.Notifier;
import com.urosjarc.headhunt.schema.Project;
import com.urosjarc.headhunt.schema.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AppModel {

    private static ConnectionSource connectionSource;

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
        connectionSource = new JdbcConnectionSource(databaseUrl);

        //This should be injected!
        TableUtils.createTable(connectionSource, Task.class);
        Task.table = DaoManager.createDao(connectionSource, Task.class);

        TableUtils.createTable(connectionSource, Project.class);
        Project.table = DaoManager.createDao(connectionSource, Project.class);

        TableUtils.createTable(connectionSource, Notifier.class);
        Notifier.table = DaoManager.createDao(connectionSource, Notifier.class);
        //injected-end

        //Migration
        Project project = new Project();
        Project project1 = new Project();
        project.setName("Sample project");
        project1.setName("New project");

        Project.table.create(project);
        Project.table.create(project1);
        //migration-end

    }

    public static void closeDB() throws SQLException {
        connectionSource.close();
    }
}
