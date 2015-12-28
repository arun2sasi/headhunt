package com.urosjarc.headhunt.preloader;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class PreloaderPresenter implements Initializable {

    @FXML
    public Label textLabel;

    @FXML
    public ProgressBar progressBar;

    @Inject
    PreloaderModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("LoadingDialog init");

        //INJECTING-VIEW
        //INJECTING-END

    }

}
