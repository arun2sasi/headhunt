package com.urosjarc.headhunt.modules.loadingDialog;

import com.airhacks.afterburner.views.FXMLView;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;


public class LoadingDialogView extends FXMLView {

    @Getter
    private LoadingDialogPresenter ctrl;

    public LoadingDialogView(String title) {

        ctrl = (LoadingDialogPresenter) this.getPresenter();

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
}
