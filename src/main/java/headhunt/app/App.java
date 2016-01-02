package headhunt.app;

import com.airhacks.afterburner.injection.Injector;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;

import headhunt.preloader.notifications.Error;
import headhunt.preloader.notifications.Update;
import org.json.simple.JSONObject;
import lombok.Getter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {

    private static @Getter String env;
    private static @Getter JSONObject config;

    @Override
    public void init() throws Exception {
        /**
         * APP PRE CHECK
         */
        notifyPreloader(new Update("Pre loading check...",-1.0));
        preCheckErrNotification();

        /**
         * ENV
         */
        notifyPreloader(new Update("Setting env...",0.2));
        env = System.getProperty("ENV") == null ? "production" : System.getProperty("ENV");

        /**
         * CONFIG
         */
        notifyPreloader(new Update("Reading configuration...",0.25));
        config = (JSONObject) AppModel.getConfig(env);

        /**
         * DATABASE
         */
        notifyPreloader(new Update("Init database...",0.5));
        AppModel.openDB(
                (String) ((JSONObject) config.get("database")).get("type"),
                (String) ((JSONObject) config.get("database")).get("url")
        );

        /**
         * SEEDING
         */
        notifyPreloader(new Update("Seeding database...",0.75));
        if(env.equals("development") == true){
            AppModel.seed();
        }

        notifyPreloader(new Update("Finishing...", 1.0));
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
         * STAGE env
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


    public void preCheckErrNotification() throws Exception {
        boolean pass = true;
        String errStr = "ERRORS:\n";
        String installPath = AppModel.getPrefs().get("installPath", null);

        if (installPath == null) {
            pass = false;
            errStr += " - App is not installed!";

        } else if (new File(installPath).exists() == false) {
            pass = false;
            errStr += " - Missing: \"" + installPath + "\"";
        }

        if (!pass) {
            notifyPreloader(new Error(errStr));
        }

    }
}
