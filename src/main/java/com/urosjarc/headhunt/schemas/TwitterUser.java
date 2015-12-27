package com.urosjarc.headhunt.schemas;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.urosjarc.headhunt.AppModel;

public class TwitterUser extends Schema {

    @Getter @Setter private String uri;
    @Getter @Setter private String name;
    @Getter @Setter private String link;
    @Getter @Setter private String location;
    @Getter @Setter private String bio;
    @Getter @Setter private Date createdTime = new Date();
    @Getter @Setter private String account;
    @Getter @Setter private String portrait;
    @Getter @Setter private List<String> websites;
    @Getter @Setter private Map<String,Integer> statistics;

    public TwitterUser() {
    }

    public static void insertOrUpdate(Object o) {

        Schema user;

        JSONObject json = (JSONObject) o;
        String uri = (String) json.get("uri");

        List<Schema> users = query(
            "select * from TwitterUser where uri = '" + uri + "'"
        );

        if(users.size() == 1){
            //Update
            user = users.get(0);
        } else if(users.size() > 1){
            //Exit
            System.out.println("Multiple duplicates in twitter users!");
            return;
        } else {
            //New
            user = new TwitterUser();
        }

        user.setUri(uri);
        user.setName((String) json.get("name"));
        user.setLink((String) json.get("link"));
        user.setLocation((String) json.get("location"));
        user.setBio((String) json.get("bio"));
        user.setAccount((String) json.get("account"));
        user.setPortrait((String) json.get("portrait"));
        user.setWebsites((List<String>) json.get("websites"));
        user.setStatistics((Map<String, Integer>) json.get("statistics"));

        user.save();
    }

    static public List<Schema> getAll(){
        return query("select * from TwitterUser");
    }

    public Integer getPoints(){
        int points = 3;
        return points;
    }

    public static List<Schema> search(List<String> locations, List<String> keywords) {
        return query("select * from TwitterUser");
    }
}
