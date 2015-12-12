package com.urosjarc.headhunt;

//INJECTING-CHILD
//INJECTING-END

import java.net.URL;
import java.util.ResourceBundle;

import com.urosjarc.headhunt.schemas.TwitterUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;

public class AppPresenter implements Initializable {

    @Inject
    AppModel appModel;

    //INJECTING-NODE
    @FXML
    private TableView<TwitterUser> resultsTable;
    //INJECTING-END

    public ObservableList<TwitterUser> results;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("AppPresenter.initialize()");

        //INJECTING-VIEW
        results = appModel.db
        //INJECTING-END

    }

}
