package com.urosjarc.headhunt.schemas;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.urosjarc.headhunt.AppModel;
import org.json.simple.JSONObject;

public class TwitterUser extends Schema {

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

    public TwitterUser(Object o) {
        JSONObject json = (JSONObject) o;
        this.uri = (String) json.get("uri");
        this.name = (String) json.get("name");
        this.link = (String) json.get("link");
        this.location = (String) json.get("location");
        this.bio = (String) json.get("bio");
        this.account = (String) json.get("account");
//        this.portraits = (List<String>) json.get("portraits");
//        this.websites = (List<String>) json.get("websites");
//        this.statistics = (Map<String, Integer>) json.get("statistics");
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
