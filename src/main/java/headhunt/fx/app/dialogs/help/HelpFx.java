package headhunt.fx.app.dialogs.help;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpFx {

	private HelpPresenter press;
	private FXMLView view;

	public HelpFx(){
		view = new HelpView();
		press = (HelpPresenter) view.getPresenter();
	}

	public void showTutorial(){
		press.initTutorial();
		init();
	}

	public void showDocumentation() {
		press.initDocumentation();
		init();
	}

	private void init(){
		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Help");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.showAndWait();
	}

}
