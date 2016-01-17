package headhunt.app.views.results;

//INJECTING-CHILD
//INJECTING-END

import headhunt.app.dialogs.userInfo.UserInfoFx;
import headhunt.schemas.Schema;
import headhunt.schemas.classes.VimeoUser;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ResultsPresenter implements Initializable {

	@FXML
	@Getter private TableView<Schema> resultsTable;
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
				new UserInfoFx(1, user);
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
}
