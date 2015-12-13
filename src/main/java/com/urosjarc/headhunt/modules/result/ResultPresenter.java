package com.urosjarc.headhunt.modules.result;

//INJECTING-CHILD
//INJECTING-END

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.inject.Inject;

public class ResultPresenter implements Initializable {

    //INJECTING-NODE
    @FXML
    private Label username;
    @FXML
    private ImageView image;
    //INJECTING-END

    @Inject
    ResultModel resultModel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Result init");
        //INJECTING-VIEW
        username.setText("This user name.");
        image.setImage(new Image("http://vignette4.wikia.nocookie.net/mrmen/images/5/52/Small.gif/revision/latest?cb=20100731114437"));
        //INJECTING-END
    }

    public static void show() {
        Stage stage = new Stage();

        ResultView resultView = new ResultView();
        Scene scene = new Scene(resultView.getView());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("New task");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
