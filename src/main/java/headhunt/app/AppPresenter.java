package headhunt.app;

//INJECTING-CHILD
//INJECTING-END

import headhunt.app.modules.results.ResultsCtrl;
import headhunt.app.modules.scrapers.ScrapersCtrl;
import headhunt.app.modules.searchDialog.SearchDialogFx;
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
		SearchDialogFx searchDialogFx = new SearchDialogFx("Find users");

		searchDialogFx.onSearchEvent(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resultsCtrl.setTableItems(FXCollections.observableArrayList(
					VimeoUser.search(searchDialogFx.getLocation(), searchDialogFx.getKeyword())
				));
			}
		});
	}

	public void showResult(){
		System.out.println("Show result");
	}

    public void exit(){
        Platform.exit();
    }

}
