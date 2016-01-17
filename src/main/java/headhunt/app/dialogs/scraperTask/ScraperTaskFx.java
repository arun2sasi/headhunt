package headhunt.app.dialogs.scraperTask;

import com.airhacks.afterburner.views.FXMLView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;


public class ScraperTaskFx {

	private ScraperTaskPresenter press;
	private FXMLView view;

	public ScraperTaskFx(String title) {

		view = new ScraperTaskView();
		press = (ScraperTaskPresenter) view.getPresenter();

		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle(title);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
}
