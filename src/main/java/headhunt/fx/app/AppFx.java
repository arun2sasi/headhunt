package headhunt.fx.app;

import com.airhacks.afterburner.injection.Injector;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;

import headhunt.Main;
import headhunt.database.Db;
import headhunt.fx.preloader.notifications.Error;
import headhunt.fx.preloader.notifications.Update;
import org.json.simple.JSONObject;
import lombok.Getter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class AppFx extends Application {

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

        /**
         * CONFIG
         */
        notifyPreloader(new Update("Reading configuration...",0.25));

        /**
         * DATABASE
         */
        notifyPreloader(new Update("Init database...",0.5));
        Db.openDB(
			Main.getDbType(),
			Main.getDbUrl()
        );

        /**
         * SEEDING
         */
        notifyPreloader(new Update("Seeding database...",0.75));
        if(Main.ENV.equals("development") == true){
            Db.seed();
        }

        notifyPreloader(new Update("Finishing...", 1.0));
        Thread.sleep(500);

    }

    @Override
    public void start(Stage stage) throws Exception {
        /**
         * DEFINE DB
         */
        ODatabaseRecordThreadLocal.INSTANCE.set(Db.getConnection().getUnderlying());

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
        Db.closeDB();
        Injector.forgetAll();
    }

    public void preCheckErrNotification() throws Exception {
        boolean pass = true;
        String errStr = "ERRORS:\n";
        String installPath = Main.getInstallPath();

        if (installPath == null) {
            pass = false;
            errStr += " - AppFx is not installed!";

        } else if (!new File(installPath).exists()) {
            pass = false;
            errStr += " - Missing: \"" + installPath + "\"";
        }

        if (!pass) {
            notifyPreloader(new Error(errStr));
        }

    }
}
