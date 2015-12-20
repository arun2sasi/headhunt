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
    public Button exitButton;
    //INJECTING-END

    @Inject
    LoadingDialogModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("LoadingDialog init");

        //INJECTING-VIEW
        //INJECTING-END

    }

    public void exit(){
        Task<Void> task = model.getTask();
        task.cancel();
    }

    public void ok(){
        Task<Void> task = model.getTask();

        progressBar.progressProperty().bind(task.progressProperty());
        textLabel.textProperty().bind(task.messageProperty());

        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ((Stage) exitButton.getScene().getWindow()).close();
                Injector.forgetAll();
            }
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

}
