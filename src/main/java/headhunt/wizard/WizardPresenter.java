package headhunt.wizard;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import com.airhacks.afterburner.views.FXMLView;
import headhunt.wizard.views.hello.HelloView;
import headhunt.wizard.views.path.PathView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class WizardPresenter implements Initializable {

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
    WizardModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("WizardPresenter.initialize()");

        finishButton.setDisable(true);
        previousButton.setDisable(true);

        //INJECTING-VIEW
        view.getChildren().addAll(
            new HelloView().getView(),
            new PathView().getView()
        );
        //INJECTING-END

    }

    @FXML
    private void previous(){
        nextButton.setDisable(false);
        finishButton.setDisable(true);

        if(model.viewIndex - 1 == 0 ) {
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
        previousButton.setDisable(false);

        if(model.viewIndex + 1 == view.getChildren().size() - 1) {
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
    private void finish(){
        System.out.println("Configure all elements!");
        finishButton.getScene().getWindow().hide();
    }

}
