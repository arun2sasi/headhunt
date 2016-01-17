package headhunt.app.modules.searchDialog;

import com.airhacks.afterburner.views.FXMLView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;


public class SearchDialogFx {

	private SearchDialogPresenter presenter;
	private FXMLView view;

	@Getter
	private SearchDialogPresenter ctrl;

	public SearchDialogFx(String title) {

		view = new SearchDialogView();
		ctrl = (SearchDialogPresenter) view.getPresenter();

		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle(title);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}

	public void onSearchEvent(EventHandler event) {
		ctrl.searchButton.setOnAction(event);
	}

	public String getLocation() {
		return ctrl.location.getText();
	}

	public String getKeyword() {
		return ctrl.keyword.getText();
	}

}
