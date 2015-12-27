package com.urosjarc.headhunt.modules.searchDialog;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


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
        stage.showAndWait();
    }

    public List<String> getLocations() {
        String locationsStr = ctrl.locations.getText();
        return Arrays.asList(locationsStr.split(","));
    }

    public List<String> getKeywords() {
        String keywords = ctrl.keywords.getText();
        return Arrays.asList(keywords.split(","));
    }
}

