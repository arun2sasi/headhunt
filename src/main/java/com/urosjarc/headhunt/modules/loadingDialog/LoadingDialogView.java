package com.urosjarc.headhunt.modules.loadingDialog;

import com.airhacks.afterburner.views.FXMLView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;

public class LoadingDialogView extends FXMLView {

    private LoadingDialogPresenter presenter;
    private Stage stage;

    public LoadingDialogView(String title) {
        presenter = (LoadingDialogPresenter) this.getPresenter();

        stage = new Stage();
        Scene scene = new Scene(this.getView());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    public void setProgress(double progress){
        presenter.progressBar.setProgress(progress);
    }

    public void setText(String text){
        presenter.textLabel.setText(text);
    }

    public boolean shouldClose(){
        return presenter.shouldClose;
    }

    public void close() {
        stage.close();
    }
}
