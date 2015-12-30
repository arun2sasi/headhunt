package com.urosjarc.headhunt.app;

import com.airhacks.afterburner.injection.Injector;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;

import javafx.application.Preloader;
import org.json.simple.JSONObject;
import lombok.Getter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static @Getter String env;
    private static @Getter JSONObject config;

    @Override
    public void init() throws Exception {

        /**
         * ENV
         */
        notifyPreloader(new Preloader.ProgressNotification(0.0));
        env = System.getProperty("ENV") == null ? "production" : System.getProperty("ENV");

        /**
         * CONFIG
         */
        notifyPreloader(new Preloader.ProgressNotification(0.25));
        config = (JSONObject) AppModel.getConfig("/config/env.json");

        /**
         * DATABASE
         */
        notifyPreloader(new Preloader.ProgressNotification(0.5));
        AppModel.openDB(
                (String) ((JSONObject) config.get("database")).get("type"),
                (String) ((JSONObject) config.get("database")).get("url")
        );

        /**
         * SEEDING
         */
        notifyPreloader(new Preloader.ProgressNotification(0.7));
        if(env.equals("development") == true){
            AppModel.seed();
        }

        notifyPreloader(new Preloader.ProgressNotification(1.0));
        Thread.sleep(500);

    }

    @Override
    public void start(Stage stage) throws Exception {
        /**
         * DEFINE DB
         */
        ODatabaseRecordThreadLocal.INSTANCE.set(AppModel.getDb().getUnderlying());

        /**
         * LOAD FXML STRUCTURE
         */
        AppView appView = new AppView();
        Scene scene = new Scene(appView.getView());

        /**
         * STAGE config
         */
        stage.setScene(scene);
        stage.setTitle("Headhunt");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        AppModel.closeDB();
        Injector.forgetAll();
    }
}
