package com.urosjarc.headhunt.preloader;

import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Preloader extends javafx.application.Preloader {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        /**
         * SET stage
         */
        this.stage = stage;

        /**
         * LOAD FXML STRUCTURE
         */
        PreloaderView preloaderView = new PreloaderView("Headhunt preloader...");
        Scene scene = new Scene(preloaderView.getView());

        /**
         * STAGE config
         */
        stage.setScene(scene);
        stage.setTitle("Loading...");
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == Type.BEFORE_START) {
            stage.close();
        }
    }

}
