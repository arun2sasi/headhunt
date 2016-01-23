package headhunt.app.views.scrapers;

//INJECTING-CHILD
//INJECTING-END

import headhunt.app.AppModel;
import headhunt.app.dialogs.scraper.ScraperFx;
import headhunt.services.ScrapeTask;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScrapersPresenter implements Initializable {

    //INJECTING-NODE
	@FXML
	private TreeTableView<Object> scrapersTable;
	@FXML
	private TreeTableColumn<Object, Object> scraperName;
	@FXML
	private TreeTableColumn<Object, Object> scraperProgress;
	@FXML
	private TreeTableColumn<Object, Object> scraperInfo;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Result init");

        //INJECTING-VIEW
        //INJECTING-END
		initScraperTable();

    }

	public void initScraperTable() {

		List<ScrapeTask> scrapeTasks = AppModel.initScraping();

		/**
		 * TODO: Get all api scraper classes
		 */
		final TreeItem<Object> apiItem0 = new TreeItem<Object>("Vimeo");

		apiItem0.getChildren().setAll();
		for(ScrapeTask scrapeTask: scrapeTasks){
			apiItem0.getChildren().add(new TreeItem<Object>(scrapeTask));
		}

		/**
		 * TREE STRUCTURE
		 */
		final TreeItem<Object> root = new TreeItem<Object>("API's");
		root.setExpanded(true);
		root.getChildren().setAll(apiItem0);
		apiItem0.setExpanded(true);
		scrapersTable.setRoot(root);

		/**
		 * COLUMN: NAME
		 */
		scraperName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
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
		scraperProgress.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
			Object object = p.getValue().getValue();
			if (object instanceof ScrapeTask) {
				ScrapeTask scrapeTask = (ScrapeTask) object;
				ProgressBar progressBar = new ProgressBar();
				progressBar.progressProperty().bind(scrapeTask.progressProperty());
				return new ReadOnlyObjectWrapper<Object>(progressBar);
			} else {
				return null;
			}
		});

		/**
		 * COLUMN: INFO
		 */
		scraperInfo.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, Object> p) -> {
			Object object = p.getValue().getValue();
			if (object instanceof ScrapeTask) {
				return new ReadOnlyObjectWrapper<Object>(((ScrapeTask) object).getValue());
			} else {
				return new ReadOnlyObjectWrapper<Object>(null);
			}
		});

	}
}
