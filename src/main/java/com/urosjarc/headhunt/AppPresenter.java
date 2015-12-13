package com.urosjarc.headhunt;

//INJECTING-CHILD
//INJECTING-END

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.urosjarc.headhunt.modules.result.ResultPresenter;
import com.urosjarc.headhunt.modules.result.ResultView;
import com.urosjarc.headhunt.schemas.TwitterUser;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.inject.Inject;

public class AppPresenter implements Initializable {

    @Inject
    AppModel appModel;

    //INJECTING-NODE
    @FXML
    private TableView<TwitterUser> resultsTable;
    @FXML
    private TableColumn<TwitterUser,Integer> resultsPoints;
    @FXML
    private TableColumn<TwitterUser,String> resultsUsername;
    @FXML
    private TableColumn<TwitterUser,String> resultsLocation;
    @FXML
    private TableColumn<TwitterUser,String> resultsAccount;
    @FXML
    private TableColumn<TwitterUser,Date> resultsCreated;
    @FXML
    private TableColumn<TwitterUser,Integer> resultsRank;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("AppPresenter.initialize()");

        initResultsTable();

        //INJECTING-VIEW
        //INJECTING-END

    }

    private void initResultsTable(){
        resultsRank.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer>(1 + resultsTable.getItems().indexOf(column.getValue())));
        resultsPoints.setCellValueFactory(new PropertyValueFactory<TwitterUser, Integer>("points"));
        resultsUsername.setCellValueFactory(new PropertyValueFactory<TwitterUser, String>("name"));
        resultsLocation.setCellValueFactory(new PropertyValueFactory<TwitterUser, String>("location"));
        resultsAccount.setCellValueFactory(new PropertyValueFactory<TwitterUser, String>("account"));
        resultsCreated.setCellValueFactory(new PropertyValueFactory<TwitterUser, Date>("createdTime"));
        resultsTable.setItems(FXCollections.observableArrayList(TwitterUser.getAll()));
    }

    public void showResult(MouseEvent event){
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            TwitterUser user = resultsTable.getSelectionModel().getSelectedItem();
            if(user != null){
                System.out.println(user.getName());

                ResultPresenter.show(1,user);

            }
        }
    }


}
