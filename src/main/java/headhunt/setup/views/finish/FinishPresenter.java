package headhunt.setup.views.finish;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FinishPresenter implements Initializable {

    @FXML public CheckBox runCheckBox;

    @FXML public CheckBox readmeCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("HelloPresenter.initialize()");

        //INJECTING-VIEW
        //INJECTING-END

    }

}
