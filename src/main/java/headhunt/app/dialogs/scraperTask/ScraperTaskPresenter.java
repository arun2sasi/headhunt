package headhunt.app.dialogs.scraperTask;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ScraperTaskPresenter implements Initializable {

    //INJECTING-NODE
	@FXML private Button cancelButton;
	@FXML private Button okButton;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

        //INJECTING-VIEW
        //INJECTING-END

    }

	public void cancel(){
		System.out.println("Cancel...");
	}
	public void ok(){
		System.out.println("Ok");
	}
}
