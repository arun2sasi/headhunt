package headhunt.setup;

//INJECTING-CHILD
//INJECTING-END

import headhunt.setup.views.finish.Finish;
import headhunt.setup.views.intro.Intro;
import headhunt.setup.views.license.License;
import headhunt.setup.views.path.Path;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupPresenter implements Initializable {

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button finishButton;

    @FXML
    private AnchorPane view;

    @Inject
    @Getter SetupModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("WizardPresenter.initialize()");

        finishButton.setDisable(true);
        previousButton.setDisable(true);

        model.viewIndex = 0;

        Intro intro = new Intro();
        License license = new License();
        Path path = new Path();
        Finish finish = new Finish();

        view.getChildren().addAll(
            intro.getView(),
            license.getView(),
            path.getView(),
            finish.getView()
        );
        //INJECTING-END

        model.setPath(path);
        model.setIntro(intro);
        model.setLicense(license);
        model.setFinish(finish);

    }

    @FXML
    private void previous(){
        nextButton.setDisable(false);
        finishButton.setDisable(true);

        if(model.viewIndex - 1 != -1 ) {
            view.getChildren().get(model.viewIndex).setVisible(false);
            model.viewIndex--;
            view.getChildren().get(model.viewIndex).setVisible(true);
        }

        if (model.viewIndex == 0){
            previousButton.setDisable(true);
        }


    }

    @FXML
    private void next(){

        if(model.viewIndex == 1 && !model.userAgreeWithTerms()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("License agreement.");
            alert.setContentText("You must agree with the application terms to procede further.");
            alert.showAndWait();
            return;
        }

        if(model.viewIndex == 2 && model.pathExists()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("ERR: Selected directory");
            alert.setContentText("One or more folders with the name 'headhunt' already exist,\nselect other directory!");
            alert.show();
            return;
        }

        previousButton.setDisable(false);
        if(model.viewIndex + 1 != view.getChildren().size()) {
            view.getChildren().get(model.viewIndex).setVisible(false);
            model.viewIndex++;
            view.getChildren().get(model.viewIndex).setVisible(true);
        }
        if(model.viewIndex == view.getChildren().size() -1){
            nextButton.setDisable(true);
            finishButton.setDisable(false);
        }

    }

    @FXML
    private void cancel(){
        System.exit(0);
    }

    @FXML
    private void finish() throws IOException, ParseException {
        model.updateProdEnv();
        finishButton.getScene().getWindow().hide();
    }

}
