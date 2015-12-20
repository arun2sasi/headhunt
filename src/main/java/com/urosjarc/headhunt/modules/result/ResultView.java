package com.urosjarc.headhunt.modules.result;

import com.airhacks.afterburner.views.FXMLView;
import com.urosjarc.headhunt.schemas.TwitterUser;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResultView extends FXMLView {

    public ResultView(int rank, TwitterUser user) {
        ResultPresenter.setRankPosition(rank);
        ResultPresenter.setUser(user);

        Stage stage = new Stage();
        Scene scene = new Scene(this.getView());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("New task");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }
}