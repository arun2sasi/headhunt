package headhunt.fx.preloader;

import headhunt.fx.preloader.notifications.Error;
import headhunt.fx.preloader.notifications.Update;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class PreloaderFx extends javafx.application.Preloader {

    private Stage stage;
    private PreloaderView view;
    private PreloaderPresenter ctrl;

    @Override
    public void start(Stage stage) throws Exception {
        /**
         * SET stage
         */
        this.stage = stage;

        /**
         * LOAD FXML STRUCTURE
         */
        view = new PreloaderView();
        ctrl = (PreloaderPresenter) view.getPresenter();
        Scene scene = new Scene(view.getView());

        /**
         * STAGE env
         */
        stage.setScene(scene);
        stage.setTitle("Loading");
		stage.setResizable(false);
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == Type.BEFORE_START) {
            stage.close();
        }
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info){
        if (info instanceof Update) {
            ctrl.getProgressBar().setProgress(((Update) info).getProgress());
            ctrl.getLoadingText().setText(((Update) info).getMessage());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if(info instanceof Error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading error.");
            alert.setContentText(((Error) info).getError());
            alert.showAndWait();
            System.exit(0);
        }

    }
}
