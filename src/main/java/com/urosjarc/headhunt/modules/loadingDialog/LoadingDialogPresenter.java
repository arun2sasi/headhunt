package com.urosjarc.headhunt.modules.loadingDialog;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import com.airhacks.afterburner.views.FXMLView;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;

import javax.inject.Inject;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingDialogPresenter implements Initializable {

    //INJECTING-NODE
    @FXML
    public Label textLabel;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button cancelButton;
    //INJECTING-END

    @Inject
    LoadingDialogModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("LoadingDialog init");

        //INJECTING-VIEW
        //INJECTING-END

    }

    public void cancel(){
        model.setShouldClose(true);
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Injector.forgetAll();
    }

    public void ok(){
        Task<Void> task = model.getTask();

        progressBar.progressProperty().bind(task.progressProperty());
        textLabel.textProperty().bind(task.messageProperty());

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

}
