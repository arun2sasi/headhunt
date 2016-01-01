package headhunt.setup;

import com.airhacks.afterburner.injection.Injector;
import com.sun.javafx.application.LauncherImpl;
import headhunt.app.App;
import headhunt.preloader.Preloader;
import headhunt.setup.views.finish.Finish;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.docopt.Docopt;

import java.util.Map;

public class Setup extends Application {

    private SetupPresenter ctrl;
    private SetupView view;

    public boolean runApp = true;
    public boolean showReadme = false;

    @Override
    public void start(Stage stage) throws Exception {

        /**
         * APP INSTALLED CHECK
         */
        if (SetupModel.installFolder() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Application is installed!");
            alert.setContentText(
                    "Application install folder has been moved!\n" +
                            "Missing path: \"" + SetupModel.installFolder() + "\""
            );
            alert.showAndWait();
            runApp = false;
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

        Finish finish = ctrl.model.getFinish();
        showReadme = finish.runApplication();
        runApp = finish.runApplication();

        System.out.println(showReadme);
        System.out.println(runApp);

        Injector.forgetAll();
    }
}
