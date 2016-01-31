package headhunt.fx.app.dialogs.settings;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import headhunt.services.VimeoApi;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPresenter implements Initializable {

    //INJECTING-NODE
	@FXML private TextField vimeoOnFailWaitLabel;
	@FXML private TextField vimeoOnSuccessWaitLabel;
	@FXML private TextField vimeoOnErrorWaitLabel;
	@FXML private Button cancelButton;
	@FXML private Button okButton;
    //INJECTING-END

	@Inject
	SettingsModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

        //INJECTING-VIEW
		vimeoOnSuccessWaitLabel.setText(Integer.toString(VimeoApi.getSleepTimeOnSuccess()));
		vimeoOnFailWaitLabel.setText(Integer.toString(VimeoApi.getSleepTimeOnFail()));
		vimeoOnErrorWaitLabel.setText(Integer.toString(VimeoApi.getSleepTimeOnError()));
        //INJECTING-END

    }

	public void cancel(){
		((Stage) cancelButton.getScene().getWindow()).close();
		Injector.forgetAll();
	}
	public void ok(){
		String error = "";
		int vimeoOnFailWait = 0;
		int vimeoOnSuccessWait = 0;
		int vimeoOnErrorWait = 0;

		try{
			vimeoOnFailWait = Integer.parseInt(vimeoOnFailWaitLabel.getText());
			if(vimeoOnFailWait <= 0) throw new NumberFormatException("Invalid number \"input\" <= 0");
		} catch (NumberFormatException e){
			error += "\n - " + e.getLocalizedMessage();
		}

		try{
			vimeoOnSuccessWait = Integer.parseInt(vimeoOnSuccessWaitLabel.getText());
			if(vimeoOnSuccessWait <= 0) throw new NumberFormatException("Invalid number \"input\" <= 0");
		} catch (NumberFormatException e){
			error += "\n - " + e.getLocalizedMessage();
		}

		try{
			vimeoOnErrorWait = Integer.parseInt(vimeoOnErrorWaitLabel.getText());
			if(vimeoOnSuccessWait <= 0) throw new NumberFormatException("Invalid number \"input\" <= 0");
		} catch (NumberFormatException e){
			error += "\n - " + e.getLocalizedMessage();
		}

		if(!error.isEmpty()){
			Alert alert = new Alert(Alert.AlertType.ERROR,"Number format exception:" + error, ButtonType.CLOSE);
			alert.showAndWait();
		} else {
			VimeoApi.setSleepTimeOnFail(vimeoOnFailWait);
			VimeoApi.setSleepTimeOnSuccess(vimeoOnSuccessWait);
			VimeoApi.setSleepTimeOnError(vimeoOnErrorWait);

			((Stage) okButton.getScene().getWindow()).close();
			Injector.forgetAll();
		}
	}
}
