package headhunt.app.modules.loadDialog;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadDialogPresenter implements Initializable {

    //INJECTING-NODE
    @FXML
    public Label textLabel;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button exitButton;
    @FXML
    public Button okButton;
    //INJECTING-END

    @Inject
    LoadDialogModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("LoadingDialog init");

        //INJECTING-VIEW
        //INJECTING-END

    }

    public void exit(){
        Task<Void> task = model.getTask();
        task.cancel();
        ((Stage) exitButton.getScene().getWindow()).close();
        Injector.forgetAll();
    }

    public void ok(){

        okButton.setDisable(true);

        Task<Void> task = model.getTask();

        progressBar.progressProperty().bind(task.progressProperty());
        textLabel.textProperty().bind(task.messageProperty());

        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                exit();
            }
        });

        Thread th = new Thread(task);
        th.start();
    }

}
