package headhunt.app.dialogs.scraperTask;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.services.ScrapeTask;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;


public class ScraperTaskFx {

	private ScraperTaskPresenter press;
	private FXMLView view;

	public ScraperTaskFx(ScrapeTask task) {

		view = new ScraperTaskView();
		press = (ScraperTaskPresenter) view.getPresenter();

		/**
		 * TODO: You stayed here.
		 * TODO: Set press variables about scrape task for edit and delete.
		 */

		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Edit scrape task");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
}
