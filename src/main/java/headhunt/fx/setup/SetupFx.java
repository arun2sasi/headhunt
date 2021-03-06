package headhunt.fx.setup;

import com.airhacks.afterburner.injection.Injector;
import headhunt.Config;
import headhunt.Main;
import headhunt.fx.app.dialogs.help.HelpFx;
import headhunt.fx.setup.views.finish.FinishCtrl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SetupFx extends Application {

    private SetupPresenter ctrl;
    private SetupView view;

    @Override
    public void start(Stage stage) throws Exception {

        /**
         * APP INSTALLED CHECK
         */
        if (Config.getInstallPath() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Application is installed!");
            alert.setContentText(
                    "Application install folder has been moved!\n" +
                            "Missing path: \"" + Config.getInstallPath() + "\""
            );
            alert.showAndWait();
			System.exit(0);
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
		stage.setResizable(false);
        stage.show();

    }


    @Override
    public void stop() throws Exception {

		view.getView().setVisible(false);
        FinishCtrl finish = ctrl.model.getFinishModule();

		HelpFx helpFx0 = new HelpFx();
		HelpFx helpFx1 = new HelpFx();

        if(finish.showReadme()) helpFx0.showDocumentation();
		if(finish.showTutorial()) helpFx1.showTutorial();

		Injector.forgetAll();

    }
}
