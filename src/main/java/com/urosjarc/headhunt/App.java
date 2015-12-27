package com.urosjarc.headhunt;

import com.airhacks.afterburner.injection.Injector;

import org.docopt.Docopt;
import org.json.simple.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class App extends Application {

    private static @Getter String env;
    private static @Getter JSONObject config;

    @Override
    public void start(Stage stage) throws Exception {
        /**
         * ENV
         */
        env = System.getProperty("ENV") == null ? "production" : System.getProperty("ENV");

        /**
         * CONFIG
         */
        config = (JSONObject) AppModel.getConfig("/config/env.json");

        /**
         * DATABASE
         */
        AppModel.openDB((String) config.get("databaseUrl"));

        /**
         * SEEDING
         */
        if(env == "development"){
            AppModel.seed();
        }

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

    public static void main(String[] args) {
        Map<String, Object> opts = new Docopt(
              "Description:\n"
            + "  Put description in here...\n"
            + "\n"
            + "Usage:\n"
            + "  headhunt\n"
            + "  headhunt (-h | --help)\n"
            + "  headhunt (-v | --version)\n"
            + "\n"
            + "Options:\n"
            + "  -v --version  App version.\n"
            + "  -h --help     Show this screen.\n"
            + "\n"
        ).withVersion("Headhunt v0.1.0").parse(args);

        launch(args);
    }
}
