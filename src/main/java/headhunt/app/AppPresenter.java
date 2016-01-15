package headhunt.app;

//INJECTING-CHILD
//INJECTING-END

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import headhunt.app.modules.loadDialog.LoadDialogView;
import headhunt.app.modules.result.ResultView;
import headhunt.app.modules.searchDialog.SearchDialogView;
import headhunt.schemas.Schema;
import headhunt.schemas.twitter.TwitterUser;
import headhunt.services.Vimeo;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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

	@FXML
	private TreeTableView<Object> scrapersTable;
	@FXML
	private TreeTableColumn<Object,Object> scraperName;
	@FXML
	private TreeTableColumn<Object,Object> scraperProgress;
	@FXML
	private TreeTableColumn<Object,Object> scraperInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("WizardPresenter.initialize()");

        initResultsTable();
		initScraperTable();

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

	private void initScraperTable() {

		Vimeo vimeo = new Vimeo();
		Task<Void> scraper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				int i = 0;
				while(true){
					updateProgress(i*0.1,10);
					i++;
					Thread.sleep(100);
				}
			}
		};

		//Creating the root element
		final TreeItem<Object> root = new TreeItem<Object>("API's");
		root.setExpanded(true);

		final TreeItem<Object> vimeoItem = new TreeItem<Object>("Vimeo");
		vimeoItem.setExpanded(true);

		//Adding tree items to the root
		root.getChildren().setAll(vimeoItem);
		vimeoItem.getChildren().setAll(new TreeItem<Object>(vimeo));

		//Defining cell content
		scraperName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
			if(p.getValue().getValue() instanceof Vimeo) {
				return new ReadOnlyObjectWrapper<Object>(((Vimeo) p.getValue().getValue()).getQuery());
			} else {
				return new ReadOnlyObjectWrapper<Object>(p.getValue().getValue());
			}
		});

		//Creating a tree table view
		scrapersTable.setRoot(root);

		scraperProgress.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
			if (p.getValue().getValue() instanceof Vimeo) {
				ProgressBar progressBar = new ProgressBar();
				progressBar.progressProperty().unbind();
				progressBar.progressProperty().bind(scraper.progressProperty());
				return new ReadOnlyObjectWrapper<Object>(progressBar);
			} else {
				return null;
			}
		});

		new Thread(scraper).start();

		scraperInfo.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
			if (p.getValue().getValue() instanceof Task) {
				return new ReadOnlyObjectWrapper<Object>(((Task) p.getValue().getValue()).getValue());
			} else {
				return new ReadOnlyObjectWrapper<Object>("");
			}
		});

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
