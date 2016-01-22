package headhunt.app;

//INJECTING-CHILD
//INJECTING-END

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import headhunt.app.dialogs.loading.LoadingFx;
import headhunt.app.dialogs.settings.SettingsFx;
import headhunt.app.views.results.ResultsCtrl;
import headhunt.app.views.scrapers.ScrapersCtrl;
import headhunt.app.dialogs.search.SearchFx;
import headhunt.schemas.classes.VimeoUser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.stage.DirectoryChooser;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AppPresenter implements Initializable {

	@Inject
	AppModel appModel;

	@FXML
	private Tab scrapersTab;
	@FXML
	private Tab resultsTab;

	private ResultsCtrl resultsCtrl;
	private ScrapersCtrl scrapersCtrl;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("WizardPresenter.initialize()");

		scrapersCtrl = new ScrapersCtrl();
		resultsCtrl = new ResultsCtrl();

		//INJECTING-VIEW
		resultsTab.setContent(resultsCtrl.getView());
		scrapersTab.setContent(scrapersCtrl.getView());
		//INJECTING-END

	}

	public void findUsers() {
		SearchFx searchDialogFx = new SearchFx("Find users");

		searchDialogFx.onSearchEvent(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resultsCtrl.setTableItems(FXCollections.observableArrayList(
                    VimeoUser.search(searchDialogFx.getLocation(), searchDialogFx.getKeyword())
				));
			}
		});
	}

	public void exportDatabase(){
		/**
		 * Directory chooser
		 */
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Export database file");
		File dir = directoryChooser.showDialog(new Stage());

		/**
		 * File to be created
		 */
		String curentDate = new SimpleDateFormat("HH:mm_dd:MM").format(new Date());
		String exportFile = new File(
			dir.getAbsolutePath(),
			"headhuntDb-" + curentDate + ".json.gz"
		).getAbsolutePath();

		/**
		 * Show loading
		 */
		LoadingFx loadingFx = new LoadingFx("Export database");
		loadingFx.setTask("Exporting database...", new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				ODatabaseRecordThreadLocal.INSTANCE.set(AppModel.getDb().getUnderlying());

				appModel.exportDB(exportFile,param -> {
					String infoText = ((String) param).replace("\n","").replace(".","");
					if(infoText!= null && !infoText.isEmpty()){
						try {
							updateMessage(infoText);
							Thread.sleep(new Long("200"));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return null;
				});
				return null;
			}
		});
	}

	public void importDatabase(){
		/**
		 * Db file chooser
		 */
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import database file");
		fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("*.json.gz", "*.json.gz"),
            new FileChooser.ExtensionFilter("All", "*.*")
		);
		File dbFile = fileChooser.showOpenDialog(new Stage());

		/**
		 * Show loading
		 */
		LoadingFx loadingFx = new LoadingFx("Import database");
		loadingFx.setTask("Importing database...", new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				ODatabaseRecordThreadLocal.INSTANCE.set(AppModel.getDb().getUnderlying());
				appModel.importDB(dbFile.getAbsolutePath(), param -> {
					String infoText = (String) param;
					infoText = infoText.replace("\n", "").replace(".", "");
					if (infoText != null && !infoText.isEmpty()) {
						try {
							updateMessage(infoText);
							Thread.sleep(new Long("200"));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return null;
				});
				return null;
			}
		});
	}

	public void loadUsers() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
		new FileChooser.ExtensionFilter("*.json", "*.json"),
		new FileChooser.ExtensionFilter("All", "*.*"));
		File file = fileChooser.showOpenDialog(new Stage());

		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));

			LoadingFx loadingFx = new LoadingFx("Loading...");

			loadingFx.setTask(
			"Importing users: " + jsonArray.size() + " users...",
			new Task<Void>() {
				private int index = 0;
				private final int size = jsonArray.size();

				@Override
				protected Void call() throws Exception {
					ODatabaseRecordThreadLocal.INSTANCE.set(AppModel.getDb().getUnderlying());

					jsonArray.forEach((Consumer) object -> {

                        if (isCancelled()) {
                            return;
                        }

                        VimeoUser.insertOrUpdate(object);
                        index++;

						updateProgress((double) index, size);
                        updateMessage("Importing users: " + index + "/" + size);
                    });

					updateMessage("Importing users: " + index + "/" + size + " SUCCESS");
					resultsCtrl.setTableItems(FXCollections.observableArrayList(VimeoUser.getAll()));

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

	public void settings(){
		SettingsFx settingsFx = new SettingsFx("Headhunt settings");
	}

	public void exit() {
		Platform.exit();
	}

}
