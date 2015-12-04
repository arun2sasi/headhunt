package com.urosjarc.headhunt;

//INJECTING-CHILD
//INJECTING-END

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;

public class AppPresenter implements Initializable {

    @Inject
    AppModel appModel;

    //INJECTING-NODE
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("AppPresenter.initialize()");

        //INJECTING-VIEW
        //INJECTING-END

    }

    @FXML
    protected void viewKarmaNewTask(ActionEvent event) {
        System.out.println("viewKarmaNewTask()");
    }

    @FXML
    protected void viewKarmaNewProject(ActionEvent event) {
        System.out.println("viewKarmaNewProject()");
    }

    @FXML
    protected void viewKarmaNewGoal(ActionEvent event) {
        System.out.println("viewKarmaNewGoal()");
    }
}
