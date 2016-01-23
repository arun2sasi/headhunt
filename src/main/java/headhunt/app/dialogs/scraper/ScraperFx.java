package headhunt.app.dialogs.scraper;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.schemas.ScraperSchema;
import headhunt.services.ScrapeTask;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ScraperFx {

	private ScraperPresenter press;
	private FXMLView view;

	public ScraperFx(ScraperSchema scraper) {

		view = new ScraperView();
		press = (ScraperPresenter) view.getPresenter();

		press.getTokenInput().setText(scraper.getToken());
		press.getNameInput().setText(scraper.getName());

		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Edit scrape task");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

		System.out.println(press.getNameInput().getText());
	}
}
