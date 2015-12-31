package headhunt.wizard.views.path;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PathPresenter implements Initializable {

    @FXML
    private Button browseButton;

    @FXML
    private TextField dirField;

    @FXML
    private Label appSpaceLabel;

    @FXML
    private Label freeSpaceLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("PathPresenter.initialize()");

        dirField.setText(System.getProperty("user.home"));


        appSpaceLabel.setText("~ 10MB");

        freeSpaceLabel.setText(
            String.format("%.2f", File.listRoots()[0].getFreeSpace() / 1000000000.00)
            + "GB"
        );

    }

    public void browse(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(dirField.getText()));
        directoryChooser.setTitle("Installation directory...");
        File selectedDir = directoryChooser.showDialog(browseButton.getScene().getWindow());

        if (selectedDir == null) {
            dirField.setText("No Directory selected");
        } else {
            dirField.setText(selectedDir.getAbsolutePath());
        }
    }

}
