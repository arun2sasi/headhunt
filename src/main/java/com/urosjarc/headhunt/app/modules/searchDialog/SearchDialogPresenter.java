package com.urosjarc.headhunt.app.modules.searchDialog;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchDialogPresenter implements Initializable {

    //INJECTING-NODE
    @FXML
    public TextField location;
    @FXML
    public TextField keyword;
    @FXML
    public Button searchButton;
    @FXML
    public Button cancelButton;
    //INJECTING-END

    @Inject
    SearchDialogModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

        //INJECTING-VIEW
        //INJECTING-END

    }

    public void search(){
        System.out.println("Search");
    }

    public void cancel(){
        ((Stage) cancelButton.getScene().getWindow()).close();
        Injector.forgetAll();
    }

}
