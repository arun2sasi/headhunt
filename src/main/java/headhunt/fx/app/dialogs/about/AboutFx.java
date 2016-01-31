package headhunt.fx.app.dialogs.about;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutFx {

	private AboutPresenter press;
	private FXMLView view;

	public AboutFx(){
		view = new AboutView();
		press = (AboutPresenter) view.getPresenter();

		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Tutorial");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.show();
	}

}
