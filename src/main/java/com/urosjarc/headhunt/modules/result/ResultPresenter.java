package com.urosjarc.headhunt.modules.result;

//INJECTING-CHILD
//INJECTING-END

import java.net.URL;
import java.util.*;

import com.urosjarc.headhunt.schemas.Schema;
import com.urosjarc.headhunt.schemas.TwitterUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;

import javax.inject.Inject;

public class ResultPresenter implements Initializable {

    @Setter static private TwitterUser user;
    @Setter static private int rankPosition;

    //INJECTING-NODE
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label rank;
    @FXML
    private Label points;
    @FXML
    private Label location;
    @FXML
    private Label accountType;
    @FXML
    private Label hyUri;
    @FXML
    private Label hyLink;
    @FXML
    private ListView activities;
    @FXML
    private ListView websites;
    @FXML
    private TextArea bio;
    //INJECTING-END

    @Inject
    ResultModel resultModel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Result init");

        //INJECTING-VIEW
        //INJECTING-END

        rank.setText(String.valueOf(rankPosition));
        image.setImage(new Image("http://newshour.s3.amazonaws.com/photos/2011/01/05/portrait-walken_slideshow.jpg"));
        name.setText(user.getName());
        points.setText(String.valueOf(user.getPoints()));
        location.setText(user.getLocation());
        accountType.setText(user.getAccount());
        hyUri.setText(user.getUri());
        hyLink.setText(user.getLink());

        ObservableList<String> activites = FXCollections.observableArrayList();
        Map<String,Integer> activitiesMap = user.getStatistics();
        for(Map.Entry<String,Integer> entry: activitiesMap.entrySet()){
            activites.add(0,entry.getKey() + "\t" + entry.getValue());
        }
        this.activities.setItems(activites);

        this.websites.setItems(
            FXCollections.observableArrayList(user.getWebsites())
        );

        bio.setText(user.getBio());
    }

}
