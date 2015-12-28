package com.urosjarc.headhunt.app.modules.searchDialog;

import com.airhacks.afterburner.views.FXMLView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

public class SearchDialogView extends FXMLView {

    @Getter
    private SearchDialogPresenter ctrl;

    public SearchDialogView(String title) {

        ctrl = (SearchDialogPresenter) this.getPresenter();

        Scene scene = new Scene(this.getView());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onSearchEvent(EventHandler event){
        ctrl.searchButton.setOnAction(event);
    }

    public String getLocation() {
        return ctrl.location.getText();
    }

    public String getKeyword() {
        return ctrl.keyword.getText();
    }
}

