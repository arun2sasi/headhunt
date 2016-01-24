package headhunt.app.dialogs.userInfo;

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
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class UserInfoPresenter implements Initializable {


    //INJECTING-NODE
    @FXML @Getter private ImageView image;
    @FXML @Getter private Label name;
    @FXML @Getter private Label rank;
    @FXML @Getter private Label points;
    @FXML @Getter private Label location;
    @FXML @Getter private Label accountType;
    @FXML @Getter private Label hyUri;
    @FXML @Getter private Label hyLink;
    @FXML @Getter private TableView<Map.Entry<String, Integer>> activityTable;
    @FXML @Getter private TableColumn<Map.Entry<String, Integer>, String> activityName;
    @FXML @Getter private TableColumn<Map.Entry<String, Integer>, Integer> activityValue;
    @FXML @Getter private ListView websites;
    @FXML @Getter private TextArea bio;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Result init");

        //INJECTING-VIEW
        //INJECTING-END

    }


}
