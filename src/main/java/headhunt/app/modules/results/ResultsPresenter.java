package headhunt.app.modules.results;

//INJECTING-CHILD
//INJECTING-END

import headhunt.app.modules.resultDialog.ResultDialogView;
import headhunt.app.modules.searchDialog.SearchDialogView;
import headhunt.schemas.Schema;
import headhunt.schemas.classes.VimeoUser;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ResultsPresenter implements Initializable {

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
        System.out.println("Result init");

        //INJECTING-VIEW
        //INJECTING-END

		initResultsTable();
    }

	public void showResult(MouseEvent event) {
		if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
			Schema user = resultsTable.getSelectionModel().getSelectedItem();
			if (user != null) {
				new ResultDialogView(1, user);
			}
		}
	}

	private void initResultsTable() {
		resultsRank.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer>(1 + resultsTable.getItems().indexOf(column.getValue())));
		resultsPoints.setCellValueFactory(new PropertyValueFactory<Schema, Integer>("points"));
		resultsUsername.setCellValueFactory(new PropertyValueFactory<Schema, String>("name"));
		resultsLocation.setCellValueFactory(new PropertyValueFactory<Schema, String>("location"));
		resultsAccount.setCellValueFactory(new PropertyValueFactory<Schema, String>("account"));
		resultsCreated.setCellValueFactory(new PropertyValueFactory<Schema, Date>("createdTime"));
		resultsTable.setItems(FXCollections.observableArrayList(VimeoUser.getAll()));
	}

	public void findUsers() {
		SearchDialogView searchDialog = new SearchDialogView("Find users");

		searchDialog.onSearchEvent(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resultsTable.setItems(FXCollections.observableArrayList(
				VimeoUser.search(searchDialog.getLocation(), searchDialog.getKeyword())
				));
			}
		});
	}
}
