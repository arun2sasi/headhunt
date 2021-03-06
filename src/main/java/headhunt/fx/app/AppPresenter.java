package headhunt.fx.app;

//INJECTING-CHILD
//INJECTING-END

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import headhunt.database.Db;
import headhunt.fx.app.dialogs.about.AboutFx;
import headhunt.fx.app.dialogs.loading.LoadingFx;
import headhunt.fx.app.dialogs.scraper.ScraperFx;
import headhunt.fx.app.dialogs.settings.SettingsFx;
import headhunt.fx.app.dialogs.help.HelpFx;
import headhunt.fx.app.views.results.ResultsCtrl;
import headhunt.fx.app.views.scrapers.ScrapersCtrl;
import headhunt.fx.app.dialogs.search.SearchFx;
import headhunt.database.classes.VimeoUser;
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
	Db appModel;

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

		if(dir==null) return;

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
				ODatabaseRecordThreadLocal.INSTANCE.set(Db.getConnection().getUnderlying());

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

		if(dbFile==null) return;

		/**
		 * Show loading
		 */
		LoadingFx loadingFx = new LoadingFx("Import database");
		loadingFx.setTask("Importing database...", new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				ODatabaseRecordThreadLocal.INSTANCE.set(Db.getConnection().getUnderlying());
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

			LoadingFx loadingFx = new LoadingFx("Import users");

			loadingFx.setTask(
			"Importing users: " + jsonArray.size() + " users...",
			new Task<Void>() {
				private int index = 0;
				private final int size = jsonArray.size();

				@Override
				protected Void call() throws Exception {
					ODatabaseRecordThreadLocal.INSTANCE.set(Db.getConnection().getUnderlying());

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
		SettingsFx settingsFx = new SettingsFx();
	}

	public void newScraper(){
		ScraperFx.create();
	}

	public void exit() {
		Platform.exit();
	}

	public void tutorials(){
		HelpFx helpFx = new HelpFx();
		helpFx.showTutorial();
	}

	public void about(){
		AboutFx aboutFx = new AboutFx();
	}

	public void documentation(){
		HelpFx helpFx = new HelpFx();
		helpFx.showDocumentation();
	}
}
