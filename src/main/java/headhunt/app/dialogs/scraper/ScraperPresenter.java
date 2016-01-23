package headhunt.app.dialogs.scraper;

//INJECTING-CHILD
//INJECTING-END

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class ScraperPresenter implements Initializable {

    //INJECTING-NODE
	@Getter @FXML private TextField nameInput;
	@Getter @FXML private TextField tokenInput;
	@FXML private Button deleteButton;
	@FXML private Button okButton;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

    }

	public void delete(){
		System.out.println("Delete...");
	}
	public void ok(){
		System.out.println("Ok...");
	}
}
