package headhunt.wizard;

import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Wizard extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        /**
         * APP INSTALLED CHECK
         */
        if (WizardModel.installFolder() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Application is installed!");
            alert.setContentText(
                "Application install folder has been moved!\n" +
                "Missing path: \"" + WizardModel.installFolder() + "\""
            );
            alert.showAndWait();
            System.exit(0);
        }

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
