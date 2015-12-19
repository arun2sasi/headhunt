package com.urosjarc.headhunt.schemas;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.urosjarc.headhunt.AppModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.criteria.CriteriaBuilder;

public class TwitterUser {

    @Getter @Setter private String uri;
    @Getter @Setter private String name;
    @Getter @Setter private String link;
    @Getter @Setter private String location;
    @Getter @Setter private String bio;
    @Getter @Setter private Date createdTime = new Date();
    @Getter @Setter private String account;
    @Getter @Setter private List<String> portraits;
    @Getter @Setter private List<String> websites;
    @Getter @Setter private Map<String,Integer> statistics;

    public TwitterUser() {
    }

    static public void importJsonFile(File file){

        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));
            System.out.println(jsonArray.get(0));
            //Todo: Import one by one in to the database...
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public ArrayList<TwitterUser> getAll(){
        return AppModel.getDb().query(new OSQLSynchQuery<TwitterUser>("select * from TwitterUser"));
    }

    public Integer getPoints(){
        int points = 3;
        return points;
    }

    public String getPortrait(){
        return portraits.get(1);
    }

}
