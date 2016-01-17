package headhunt.app.dialogs.loading;

import com.airhacks.afterburner.views.FXMLView;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;


public class LoadingView extends FXMLView {

    @Getter
    private LoadingPresenter ctrl;

    public LoadingView(String title) {

        ctrl = (LoadingPresenter) this.getPresenter();

        Scene scene = new Scene(this.getView());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void setProgress(double progress){
        ctrl.progressBar.setProgress(progress);
    }

    public void setTask(String text, Task<Void> task){
        ctrl.textLabel.setText(text);
        ctrl.model.setTask(task);
    }

    public void setText(String text) {
        ctrl.textLabel.setText(text);
    }
}
