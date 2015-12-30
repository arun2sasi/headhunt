package headhunt.wizard;

import com.airhacks.afterburner.injection.Injector;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import headhunt.preloader.Notification;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.json.simple.JSONObject;

public class Wizard extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        /**
         * LOAD FXML STRUCTURE
         */
        WizardView appView = new WizardView();
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
        Injector.forgetAll();
    }
}
