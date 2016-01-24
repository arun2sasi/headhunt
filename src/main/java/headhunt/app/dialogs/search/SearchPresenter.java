package headhunt.app.dialogs.search;

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

public class SearchPresenter implements Initializable {

    //INJECTING-NODE
    @FXML
    public TextField location;
    @FXML
    public TextField keyword;
    @FXML
    public Button searchButton;
    @FXML
    public Button exitButton;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

        //INJECTING-VIEW
        //INJECTING-END

    }

    public void search(){
        System.out.println("Search");
    }

    public void exit(){
        ((Stage) exitButton.getScene().getWindow()).close();
        Injector.forgetAll();
    }

}
