package headhunt.preloader;

import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
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
         * STAGE config
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
        if (info instanceof Notification) {
            ctrl.progressBar.setProgress(((Notification) info).getProgress());
            ctrl.textLabel.setText(((Notification) info).getMessage());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
