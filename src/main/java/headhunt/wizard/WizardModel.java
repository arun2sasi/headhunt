package headhunt.wizard;

import headhunt.app.AppModel;
import headhunt.wizard.views.finish.Finish;
import headhunt.wizard.views.intro.Intro;
import headhunt.wizard.views.license.License;
import headhunt.wizard.views.path.Path;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WizardModel {

    public int viewIndex = 0;

    @Setter
    private Path path;
    @Setter
    private Finish finish;
    @Setter
    private License license;
    @Setter
    private Intro intro;

    @PostConstruct
    public void init() {
        System.out.println("Wizard.init()");
    }

    public static String installFolder(){
        JSONObject folderObj = (JSONObject) getProdEnv().get("folder");

        return (String) folderObj.get("install");
    }

    public static JSONObject getProdEnv(){
        InputStream jsonStream = AppModel.class.getResourceAsStream("/env/production.json");


        JSONObject prodEnv = null;

        try {
            JSONParser parser = new JSONParser();
            prodEnv = (JSONObject) parser.parse(new InputStreamReader(jsonStream));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return prodEnv;

    }

    public void updateProdEnv() {

        //Production path
        URL jsonURL = this.getClass().getResource("/env/production.json");

        //Make dir
        File configDir = new File(path.getPath());
        configDir.setExecutable(true, false);
        configDir.setReadable(true, false);
        configDir.setWritable(true, false);
        //configDir.mkdir();

        //Set resource evn data
        JSONObject prodEnv = getProdEnv();
        JSONObject newJsonObj = new JSONObject();
        JSONObject folderObj = (JSONObject) prodEnv.get("folder");
        JSONObject databaseObj = (JSONObject) prodEnv.get("database");

        //Setting newJsonObj
        folderObj.put("install", configDir.getPath());
        newJsonObj.put("folder", folderObj);
        newJsonObj.put("database", databaseObj);

        //Write to file
        try {
            java.nio.file.Path path = Paths.get(jsonURL.toURI());
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path);
            bufferedWriter.write(newJsonObj.toJSONString());
            bufferedWriter.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( URISyntaxException e ) {
            e.printStackTrace();
        }
    }

    public boolean userAgreeWithTerms(){

        return license.userChoice();

    }
}

