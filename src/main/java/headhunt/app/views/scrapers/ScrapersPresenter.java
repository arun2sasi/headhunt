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
	public TreeTableView<Object> scrapersTable;
	public TreeTableColumn<Object, Object> scraperName;
	public TreeTableColumn<Object, Object> scraperProgress;
	public TreeTableColumn<Object, Object> scraperInfo;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Result init");

        //INJECTING-VIEW
        //INJECTING-END

    }

}
