package headhunt.wizard;

//INJECTING-CHILD
//INJECTING-END

import headhunt.app.AppModel;
import headhunt.wizard.views.intro.Intro;
import headhunt.wizard.views.license.License;
import headhunt.wizard.views.path.Path;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class WizardPresenter implements Initializable {

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button finishButton;

    @FXML
    private AnchorPane view;

    @Inject
    WizardModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("WizardPresenter.initialize()");

        finishButton.setDisable(true);
        previousButton.setDisable(true);

        model.viewIndex = 0;

        //INJECTING-VIEW
        Path path = new Path();
        view.getChildren().addAll(
            new Intro().getView(),
            new License().getView(),
            path.getView()
        );
        //INJECTING-END

        model.path = path;

    }

    @FXML
    private void previous(){
        nextButton.setDisable(false);
        finishButton.setDisable(true);

        if(model.viewIndex - 1 != -1 ) {
            view.getChildren().get(model.viewIndex).setVisible(false);
            model.viewIndex--;
            view.getChildren().get(model.viewIndex).setVisible(true);
        }

        if (model.viewIndex == 0){
            previousButton.setDisable(true);
        }


    }

    @FXML
    private void next(){
        previousButton.setDisable(false);

        if(model.viewIndex + 1 != view.getChildren().size()) {
            view.getChildren().get(model.viewIndex).setVisible(false);
            model.viewIndex++;
            view.getChildren().get(model.viewIndex).setVisible(true);
        }
        if(model.viewIndex == view.getChildren().size() -1){
            nextButton.setDisable(true);
            finishButton.setDisable(false);
        }

    }

    @FXML
    private void cancel(){
        System.exit(0);
    }

    @FXML
    private void finish() throws IOException, ParseException {

        //Production path
        InputStream jsonStream = AppModel.class.getResourceAsStream("/env/production.json");
        URL jsonURL = AppModel.class.getResource("/env/production.json");

        //Make dir
        File configDir = new File(model.path.getPath());
        configDir.setExecutable(true, false);
        configDir.setReadable(true, false);
        configDir.setWritable(true, false);
        //configDir.mkdir();

        //Set resource evn data
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject) parser.parse(new InputStreamReader(jsonStream));
        JSONObject newJsonObj = new JSONObject();
        JSONObject folderObj = (JSONObject) jsonObj.get("folder");
        JSONObject databaseObj = (JSONObject) jsonObj.get("database");

        //Setting newJsonObj
        folderObj.put("install" , configDir.getPath());
        newJsonObj.put("folder" , folderObj);
        newJsonObj.put("database", databaseObj);

        //Write to file
        try {
            java.nio.file.Path path = Paths.get(jsonURL.toURI());
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path);
            bufferedWriter.write(newJsonObj.toJSONString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

}
