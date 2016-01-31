package headhunt.fx.app.dialogs.scraper;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.fx.app.dialogs.scraper.create.CreateView;
import headhunt.fx.app.dialogs.scraper.edit.EditPresenter;
import headhunt.fx.app.dialogs.scraper.edit.EditView;
import headhunt.database.schemas.ScraperSchema;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ScraperFx {

	public static void edit(ScraperSchema scraper) {

		FXMLView view = new EditView();
		EditPresenter press = (EditPresenter) view.getPresenter();
		press.setScraper(scraper);

		press.getTokenInput().setText(scraper.getToken());
		press.getNameInput().setText(scraper.getName());

		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Edit scraper");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

	}

	public static void create() {
		FXMLView view = new CreateView();
		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Create scraper");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
}
