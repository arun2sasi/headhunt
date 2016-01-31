package headhunt.fx.app.dialogs.loading;

import com.airhacks.afterburner.views.FXMLView;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoadingFx {

	private LoadingPresenter press;
	private FXMLView view;

	public LoadingFx(String title) {

		view = new LoadingView();
		press = (LoadingPresenter) view.getPresenter();

		Scene scene = new Scene(view.getView());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle(title);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}

	public void setProgress(double progress) {
		press.progressBar.setProgress(progress);
	}

	public void setTask(String text, Task<Void> task) {
		press.textLabel.setText(text);
		press.model.setTask(task);
	}

	public void setText(String text) {
		press.textLabel.setText(text);
	}
}
