package headhunt.wizard;

import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Wizard extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        /**
         * LOAD FXML STRUCTURE
         */
        WizardView wizardView = new WizardView();
        Scene scene = new Scene(wizardView.getView());

        /**
         * STAGE config
         */
        stage.setScene(scene);
        stage.setTitle("Setup wizard");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }
}
