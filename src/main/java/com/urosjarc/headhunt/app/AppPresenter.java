package com.urosjarc.headhunt.app;

//INJECTING-CHILD
//INJECTING-END

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.urosjarc.headhunt.app.modules.loadDialog.LoadDialogView;
import com.urosjarc.headhunt.app.modules.result.ResultView;
import com.urosjarc.headhunt.app.modules.searchDialog.SearchDialogView;
import com.urosjarc.headhunt.schemas.Schema;
import com.urosjarc.headhunt.schemas.TwitterUser;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;

public class AppPresenter implements Initializable {

    @Inject
    AppModel appModel;

    @FXML
    private TableView<Schema> resultsTable;
    @FXML
    private TableColumn<Schema, Integer> resultsPoints;
    @FXML
    private TableColumn<Schema, String> resultsUsername;
    @FXML
    private TableColumn<Schema, String> resultsLocation;
    @FXML
    private TableColumn<Schema, String> resultsAccount;
    @FXML
    private TableColumn<Schema, Date> resultsCreated;
    @FXML
    private TableColumn<Schema, Integer> resultsRank;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("AppPresenter.initialize()");

        initResultsTable();

        //INJECTING-VIEW
        //INJECTING-END

    }

    private void initResultsTable(){
        resultsRank.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer>(1 + resultsTable.getItems().indexOf(column.getValue())));
        resultsPoints.setCellValueFactory(new PropertyValueFactory<Schema, Integer>("points"));
        resultsUsername.setCellValueFactory(new PropertyValueFactory<Schema, String>("name"));
        resultsLocation.setCellValueFactory(new PropertyValueFactory<Schema, String>("location"));
        resultsAccount.setCellValueFactory(new PropertyValueFactory<Schema, String>("account"));
        resultsCreated.setCellValueFactory(new PropertyValueFactory<Schema, Date>("createdTime"));
        resultsTable.setItems(FXCollections.observableArrayList(TwitterUser.getAll()));
    }

    public void showResult(MouseEvent event){
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Schema user = resultsTable.getSelectionModel().getSelectedItem();
            if(user != null){
                new ResultView(1, user);
            }
        }
    }

    public void importTwitterUsers(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.json", "*.json"),
                new FileChooser.ExtensionFilter("All", "*.*"));
        File file = fileChooser.showOpenDialog(new Stage());

        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));

            LoadDialogView loadingDialog = new LoadDialogView("Loading...");

            loadingDialog.setTask(
                "Importing twitter users: " + jsonArray.size() + " users...",
                new Task<Void>() {
                    private int index = 0;
                    private final int size = jsonArray.size();

                    @Override
                    protected Void call() throws Exception {
                        ODatabaseRecordThreadLocal.INSTANCE.set(AppModel.getDb().getUnderlying());

                        index = 0;

                        jsonArray.forEach(new Consumer() {
                            @Override
                            public void accept(Object object) {

                                if (isCancelled()) {
                                    return;
                                }

                                TwitterUser.insertOrUpdate(object);
                                updateProgress((double) index,size);
                                index++;
                                updateMessage("Importing twitter users: " + index + "/" + size);
                            }
                        });

                        updateMessage("Importing twitter users: " + index + "/" + size + " SUCCESS");
                        resultsTable.setItems(FXCollections.observableArrayList(TwitterUser.getAll()));

                        return null;
                    }
            });



        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void findUsers(){
        SearchDialogView searchDialog = new SearchDialogView("Find users");

        searchDialog.onSearchEvent(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                resultsTable.setItems(FXCollections.observableArrayList(
                        TwitterUser.search(searchDialog.getLocation(),searchDialog.getKeyword())
                ));
            }

        });

    }

    public void exit(){
        Platform.exit();
    }

}
