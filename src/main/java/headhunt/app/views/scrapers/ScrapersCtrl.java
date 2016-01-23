package headhunt.app.views.scrapers;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.app.AppModel;
import headhunt.app.dialogs.scraper.ScraperFx;
import headhunt.services.ScrapeTask;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.util.List;

//Todo: Make every partial view like this...
//Todo: Model should be only ram extension from presenter (what should not be in module api (ctrl/fx).

public class ScrapersCtrl {

	private static ScrapersPresenter press;
	private FXMLView view;

	public ScrapersCtrl() {
		view = new ScrapersView();
		press = (ScrapersPresenter) view.getPresenter();
		updateScrapersTable();
	}

	public Parent getView() {
		return view.getView();
	}

	public static void updateScrapersTable() {

		List<ScrapeTask> scrapeTasks = AppModel.initScraping();

		/**
		 * TODO: Get all api scraper classes
		 */
		final TreeItem<Object> apiItem0 = new TreeItem<Object>("Vimeo");

		apiItem0.getChildren().setAll();
		for (ScrapeTask scrapeTask : scrapeTasks) {
			apiItem0.getChildren().add(new TreeItem<Object>(scrapeTask));
		}

		/**
		 * TREE STRUCTURE
		 */
		final TreeItem<Object> root = new TreeItem<Object>("API's");
		root.setExpanded(true);
		root.getChildren().setAll(apiItem0);
		apiItem0.setExpanded(true);
		press.scrapersTable.setRoot(root);

		/**
		 * COLUMN: NAME
		 */
		press.scraperName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
			Object object = p.getValue().getValue();
			if (object instanceof ScrapeTask) {
				String taskName = ((ScrapeTask) object).getScraper().getName();
				Label nameLabel = new Label(taskName);
				nameLabel.setOnMouseClicked(event -> {
					if (event.getButton().equals(MouseButton.PRIMARY)) {
						if (event.getClickCount() == 2) {
							ScraperFx.edit(((ScrapeTask) object).getScraper());
						}
					}
				});
				return new ReadOnlyObjectWrapper<Object>(nameLabel);
			} else {
				return new ReadOnlyObjectWrapper<Object>(object);
			}
		});

		/**
		 * COLUMN: PROGRESS
		 */
		press.scraperProgress.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
			Object object = p.getValue().getValue();
			if (object instanceof ScrapeTask) {
				ScrapeTask scrapeTask = (ScrapeTask) object;
				ProgressBar progressBar = new ProgressBar();
				progressBar.progressProperty().bind(scrapeTask.progressProperty());

				VBox vbox = new VBox();
				vbox.setAlignment(Pos.TOP_CENTER);
				progressBar.prefWidthProperty().bind(vbox.widthProperty().subtract(0));
				vbox.getChildren().add(progressBar);

				return new ReadOnlyObjectWrapper<Object>(vbox);
			} else {
				return null;
			}
		});

		/**
		 * COLUMN: INFO
		 */
		press.scraperInfo.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
			Object object = p.getValue().getValue();
			if (object instanceof ScrapeTask) {
				ScrapeTask scrapeTask = (ScrapeTask) object;
				Label infoLabel = new Label();
				infoLabel.textProperty().bind(scrapeTask.messageProperty());
				return new ReadOnlyObjectWrapper<Object>(infoLabel);
			} else {
				return new ReadOnlyObjectWrapper<Object>(null);
			}
		});
	}
}

