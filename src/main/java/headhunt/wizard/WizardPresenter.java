package headhunt.wizard;

//INJECTING-CHILD
//INJECTING-END

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import headhunt.app.modules.loadDialog.LoadDialogView;
import headhunt.app.modules.result.ResultView;
import headhunt.app.modules.searchDialog.SearchDialogView;
import headhunt.schemas.Schema;
import headhunt.schemas.TwitterUser;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class WizardPresenter implements Initializable {

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button finishButton;

    @Inject
    WizardModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("WizardPresenter.initialize()");

        //INJECTING-VIEW
        //INJECTING-END

    }

    @FXML
    private void previous(){}

    @FXML
    private void next(){}

    @FXML
    private void cancel(){}

    @FXML
    private void finish(){}

}
