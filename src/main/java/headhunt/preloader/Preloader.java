package headhunt.preloader;

import headhunt.preloader.notifications.Error;
import headhunt.preloader.notifications.Update;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Preloader extends javafx.application.Preloader {

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
        stage.setTitle("Setup application");
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
            ctrl.progressBar.setProgress(((Update) info).getProgress());
            ctrl.textLabel.setText(((Update) info).getMessage());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if(info instanceof Error){
            ctrl.textLabel.setText("ERROR");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading error.");
            alert.setContentText(((Error) info).getError());
            alert.showAndWait();
        }

    }
}
