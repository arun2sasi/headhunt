package headhunt.app.dialogs.scraper.create;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import headhunt.app.dialogs.scraper.ScraperFx;
import headhunt.app.views.scrapers.ScrapersCtrl;
import headhunt.schemas.ScraperSchema;
import headhunt.schemas.classes.VimeoUsersScraper;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatePresenter implements Initializable {

    //INJECTING-NODE
	@Getter @FXML private TextField nameInput;
	@Getter @FXML private TextField tokenInput;
	@Getter @FXML private TextField queryInput;
	@FXML private MenuButton classMenuButton;
	@FXML private Button createButton;
	@FXML private Button cancelButton;
    //INJECTING-END

	@Inject
	CreateModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

		classMenuButton.getItems().setAll( model.getVimeoUsersItem());

		for(MenuItem menuItem: classMenuButton.getItems()){
			menuItem.setOnAction(event -> {
				MenuItem selectedItem = (MenuItem) event.getTarget();
				model.setSelectedItem(selectedItem);
				classMenuButton.setText(selectedItem.getText());
			});
		}
    }

	public void create(){

		if(model.getSelectedItem() == model.getVimeoUsersItem()){

			new VimeoUsersScraper(
				nameInput.getText(),
				tokenInput.getText(),
				queryInput.getText()
			).save();

			ScrapersCtrl.updateScrapersTable();
		}

	}

	public void cancel(){
		((Stage) cancelButton.getScene().getWindow()).close();
		Injector.forgetAll();
	}
}
