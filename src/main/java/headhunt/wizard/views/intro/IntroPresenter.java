package headhunt.wizard.views.intro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class IntroPresenter implements Initializable {

    @FXML
    private ImageView setupImage;

    @FXML
    private TextFlow header;

    @FXML
    private TextFlow info;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("HelloPresenter.initialize()");

        //INJECTING-VIEW
        //INJECTING-END

    }

}
