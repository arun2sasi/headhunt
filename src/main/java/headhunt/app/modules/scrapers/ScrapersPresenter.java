package headhunt.app.modules.scrapers;

//INJECTING-CHILD
//INJECTING-END

import headhunt.app.AppModel;
import headhunt.schemas.classes.VimeoUser;
import headhunt.schemas.records.Portrait;
import headhunt.schemas.records.Website;
import headhunt.services.ScrapeTask;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import lombok.Setter;

import javax.inject.Inject;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	private void initScraperTable() {

		List<ScrapeTask> scrapeTasks = AppModel.initScraping();

		/**
		 * TODO: Get all api scraper classes
		 */
		final TreeItem<Object> apiItem0 = new TreeItem<Object>("Vimeo");
		apiItem0.getChildren().setAll(new TreeItem<Object>(scrapeTasks.get(0)));

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
				return new ReadOnlyObjectWrapper<Object>(((ScrapeTask) object).getName());
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
				ProgressBar progressBar = new ProgressBar();
				progressBar.progressProperty().unbind();
				progressBar.progressProperty().bind(((ScrapeTask) object).progressProperty());
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
