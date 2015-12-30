package com.urosjarc.headhunt.preloader;

import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.beans.binding.DoubleBinding;
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
        stage.setTitle("Loading... 0 %");
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

        if (info instanceof ProgressNotification) {
            ProgressNotification PN = (ProgressNotification) info;
            ctrl.progressBar.setProgress(PN.getProgress());
            ctrl.textLabel.setText("Loading... " + PN.getProgress() * 100 + " %");
            sleep();
        }

    }

    public void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
