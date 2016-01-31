package headhunt.fx.app.dialogs.scraper.edit;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import headhunt.fx.app.views.scrapers.ScrapersCtrl;
import headhunt.database.schemas.ScraperSchema;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPresenter implements Initializable {

	@Getter @Setter private ScraperSchema scraper;

    //INJECTING-NODE
	@Getter @FXML private TextField nameInput;
	@Getter @FXML private TextField tokenInput;
	@FXML private Button deleteButton;
	@FXML private Button updateButton;
	@FXML private Button cancelButton;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");
    }

	public void delete(){
		scraper.delete();
		ScrapersCtrl.updateScrapersTable();
		((Stage) deleteButton.getScene().getWindow()).close();
		Injector.forgetAll();
	}
	public void update(){
		scraper.setName(nameInput.getText());
		scraper.setToken(tokenInput.getText());
		scraper.save();
		ScrapersCtrl.updateScrapersTable();
		((Stage) updateButton.getScene().getWindow()).close();
		Injector.forgetAll();
	}
	public void cancel(){
		((Stage) cancelButton.getScene().getWindow()).close();
		Injector.forgetAll();
	}
}
