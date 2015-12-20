package com.urosjarc.headhunt;

//INJECTING-CHILD
//INJECTING-END

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import com.orientechnologies.orient.object.db.OObjectDatabaseTxPooled;
import com.urosjarc.headhunt.modules.loadingDialog.LoadingDialogPresenter;
import com.urosjarc.headhunt.modules.loadingDialog.LoadingDialogView;
import com.urosjarc.headhunt.modules.result.ResultPresenter;
import com.urosjarc.headhunt.modules.result.ResultView;
import com.urosjarc.headhunt.schemas.TwitterUser;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;

public class AppPresenter implements Initializable {

    @Inject
    AppModel appModel;

    @FXML
    private TableView<TwitterUser> resultsTable;
    @FXML
    private TableColumn<TwitterUser, Integer> resultsPoints;
    @FXML
    private TableColumn<TwitterUser, String> resultsUsername;
    @FXML
    private TableColumn<TwitterUser, String> resultsLocation;
    @FXML
    private TableColumn<TwitterUser, String> resultsAccount;
    @FXML
    private TableColumn<TwitterUser, Date> resultsCreated;
    @FXML
    private TableColumn<TwitterUser, Integer> resultsRank;

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

                new ResultView(1,user);

            }
        }
    }

    public void importTwitterUsers(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Json files", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(new Stage());

        JSONParser parser = new JSONParser();
        try {

            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation dialog");
            alert.setContentText("Do you want to import " + jsonArray.size() + " json objects?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {

                LoadingDialogView loadingDialogView = new LoadingDialogView("Loading...");
                loadingDialogView.setText("Importing twitter users...");

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        ODatabaseRecordThreadLocal.INSTANCE.set(AppModel.getDb().getUnderlying());
                        jsonArray.forEach(new Consumer() {
                            private int index = 0;
                            private int size = jsonArray.size();
                            @Override
                            public void accept(Object object) {

                                if(loadingDialogView.shouldClose()) return;

                                TwitterUser.insertOrUpdate(object);
                                loadingDialogView.setProgress(index/(double) size);
                                index++;
                            }
                        });
                        resultsTable.setItems(FXCollections.observableArrayList(TwitterUser.getAll()));
                        return null;
                    }
                };

                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        loadingDialogView.close();
                    }
                });

                Thread th = new Thread(task);
                th.setDaemon(true);
                th.start();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exit(){
        Platform.exit();
    }


}
