package headhunt.fx.app.dialogs.settings;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SettingsFx {

	private SettingsPresenter press;
	private FXMLView view;

	public SettingsFx(String title) {

		view = new SettingsView();
		press = (SettingsPresenter) view.getPresenter();

		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle(title);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
}
