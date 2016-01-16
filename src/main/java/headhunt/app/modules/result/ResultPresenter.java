package headhunt.app.modules.result;

//INJECTING-CHILD
//INJECTING-END

import headhunt.schemas.classes.VimeoUser;
import headhunt.schemas.records.Portrait;
import headhunt.schemas.records.Website;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import lombok.Setter;

import javax.inject.Inject;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ResultPresenter implements Initializable {

    @Setter
    static private VimeoUser user;
    @Setter
    static private int rankPosition;

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
    private TableView<Map.Entry<String, Integer>> activityTable;
    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> activityName;
    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> activityValue;
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
        if(user.getPortraits().size() == 0){
            image.setImage(new Image(this.getClass().getResourceAsStream("/media/incognito.jpg")));
        } else {
            Portrait maxPortrait = user.getPortraits().get(0);
            for(Portrait portrait: user.getPortraits()){
                if(maxPortrait.getHeight() < portrait.getHeight()){
                    maxPortrait = portrait;
                }
            }
            image.setImage(new Image(maxPortrait.getLink()));
        }
        name.setText(user.getName());
        points.setText(String.valueOf(user.getPoints()));
        location.setText(user.getLocation());
        accountType.setText(user.getAccount());
        hyUri.setText(user.getUri());
        hyLink.setText(user.getLink());
        bio.setText(user.getBio());

        //Set websites
        initWebsiteList(user.getWebsites());

        //Set activities
        initActivityTable(user.getStatistics());

    }

    private void initWebsiteList(List<Website> websites){
        //Set websites
        ObservableList<Website> websitesObList = FXCollections.observableArrayList(websites);
        this.websites.setCellFactory(new Callback<ListView<Website>, ListCell<Website>>() {
            @Override
            public ListCell<Website> call(ListView<Website> p) {

                ListCell<Website> cell = new ListCell<Website>() {

                    @Override
                    protected void updateItem(Website t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            String text = "";
                            if(t.getName() != null){
                                text += t.getName();
                            }
                            if(t.getDescription() != null){
                                text += " : " + t.getDescription();
                            }
                            if(t.getLink() != null){
                                text += " : " + t.getLink();
                            }

                            setText(text);
                        }

                    }

                };
                return cell;
            }
        });
        this.websites.setItems(websitesObList);
    }

    private void initActivityTable(Map<String,Integer> map) {

        activityName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        activityValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer>, ObservableValue<Integer>>() {

            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer> p) {
                return new SimpleObjectProperty<Integer>(p.getValue().getValue());
            }
        });

        Map<String,Integer> newMap = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            Object value = (Object) entry.getValue();
            if(!value.toString().equals("0")) newMap.put(entry.getKey(),new Integer(value.toString()));
        }

        ObservableList<Map.Entry<String, Integer>> items = FXCollections.observableArrayList(newMap.entrySet());

        activityTable.setItems(items);
    }
}
