package headhunt.setup;

import com.airhacks.afterburner.injection.Injector;
import headhunt.setup.views.finish.Finish;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Setup extends Application {

    private SetupPresenter ctrl;
    private SetupView view;

    @Override
    public void start(Stage stage) throws Exception {

        /**
         * APP INSTALLED CHECK
         */
        if (SetupModel.getInstallPath() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Application is installed!");
            alert.setContentText(
                    "Application install folder has been moved!\n" +
                            "Missing path: \"" + SetupModel.getInstallPath() + "\""
            );
            alert.showAndWait();
            return;
        }

        /**
         * LOAD FXML STRUCTURE
         */
        view = new SetupView();
        Scene scene = new Scene(view.getView());

        /**
         * SETUP CTRL
         */
        ctrl = (SetupPresenter) view.getPresenter();

        /**
         * STAGE env
         */
        stage.setScene(scene);
        stage.setTitle("Setup");
        stage.show();

    }


    @Override
    public void stop() throws Exception {

        Finish finish = ctrl.model.getFinishModule();
        if(finish.showReadme()){
            System.out.println("Show readme...");
        }
        Injector.forgetAll();
    }
}