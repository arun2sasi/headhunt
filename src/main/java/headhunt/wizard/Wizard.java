package headhunt.wizard;

import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Wizard extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /**
         * CHECK FOR EXISTING INSTALLATION
         */
        //Todo: Here you check if application is allready installed...

        /**
         * LOAD FXML STRUCTURE
         */
        WizardView wizardView = new WizardView();
        Scene scene = new Scene(wizardView.getView());

        /**
         * STAGE env
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
