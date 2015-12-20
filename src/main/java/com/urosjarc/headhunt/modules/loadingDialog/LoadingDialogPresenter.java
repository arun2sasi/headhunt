package com.urosjarc.headhunt.modules.loadingDialog;

//INJECTING-CHILD
//INJECTING-END

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import lombok.Getter;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingDialogPresenter implements Initializable {

    public boolean shouldClose = false;

    //INJECTING-NODE
    @FXML
    public Label textLabel;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button cancelButton;
    //INJECTING-END

    @Inject
    LoadingDialogModel resultModel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("LoadingDialog init");

        //INJECTING-VIEW
        //INJECTING-END

    }

    public void cancel(){
        shouldClose = true;
    }

}
