package com.urosjarc.headhunt;

//INJECTING-CHILD
//INJECTING-END

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.urosjarc.headhunt.schemas.TwitterUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;

public class AppPresenter implements Initializable {

    @Inject
    AppModel appModel;

    //INJECTING-NODE
    @FXML
    private TableView<TwitterUser> resultsTable;

    @FXML
    private TableColumn<TwitterUser,String> resultsUsername;
    //INJECTING-END

    public ObservableList<TwitterUser> results;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("AppPresenter.initialize()");

        //INJECTING-VIEW
        TwitterUser user = new TwitterUser();
        user.setName("Uros");
        List<TwitterUser> listUser = new ArrayList<TwitterUser>();
        listUser.add(user);

        System.out.println(resultsUsername);
        resultsUsername.setCellValueFactory(new PropertyValueFactory<TwitterUser, String>("name"));
        resultsTable.setItems(FXCollections.observableArrayList(listUser));
        //INJECTING-END

    }

}
