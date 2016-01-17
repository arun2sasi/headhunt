package headhunt.app;

//INJECTING-CHILD
//INJECTING-END

import headhunt.app.modules.results.ResultsCtrl;
import headhunt.app.modules.scrapers.ScrapersCtrl;
import headhunt.app.modules.searchDialog.SearchDialogView;
import headhunt.schemas.classes.VimeoUser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AppPresenter implements Initializable {

    @Inject
    AppModel appModel;

	@FXML private Tab scrapersTab;
	@FXML private Tab resultsTab;

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

	public void findUsers(){
		SearchDialogView searchDialog = new SearchDialogView("Find users");

		searchDialog.onSearchEvent(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resultsCtrl.setTableItems(FXCollections.observableArrayList(
					VimeoUser.search(searchDialog.getLocation(), searchDialog.getKeyword())
				));
			}
		});
	}

	public void showResult(){
		System.out.println("Show result");
	}

//    public void importTwitterUsers(){
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Resource File");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("*.json", "*.json"),
//                new FileChooser.ExtensionFilter("All", "*.*"));
//        File file = fileChooser.showOpenDialog(new Stage());
//
//        JSONParser parser = new JSONParser();
//        try {
//            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));
//
//            LoadDialogView loadingDialog = new LoadDialogView("Loading...");
//
//            loadingDialog.setTask(
//                "Importing twitter users: " + jsonArray.size() + " users...",
//                new Task<Void>() {
//                    private int index = 0;
//                    private final int size = jsonArray.size();
//
//                    @Override
//                    protected Void call() throws Exception {
//                        ODatabaseRecordThreadLocal.INSTANCE.set(AppModel.getDb().getUnderlying());
//
//                        index = 0;
//
//                        jsonArray.forEach(new Consumer() {
//                            @Override
//                            public void accept(Object object) {
//
//                                if (isCancelled()) {
//                                    return;
//                                }
//
//                                VimeoUser.insertOrUpdate(object);
//                                updateProgress((double) index,size);
//                                index++;
//                                updateMessage("Importing twitter users: " + index + "/" + size);
//                            }
//                        });
//
//                        updateMessage("Importing twitter users: " + index + "/" + size + " SUCCESS");
//                        resultsTable.setItems(FXCollections.observableArrayList(VimeoUser.getAll()));
//
//                        return null;
//                    }
//            });
//
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void exit(){
        Platform.exit();
    }

}
