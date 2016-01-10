package headhunt.schemas.twitter;

import headhunt.schemas.Schema;
import headhunt.schemas.records.Portrait;
import headhunt.schemas.records.Website;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwitterUser extends Schema {

    @Getter @Setter private String uri;
    @Getter @Setter private String name;
    @Getter @Setter private String link;
    @Getter @Setter private String location;
    @Getter @Setter private String bio;
    @Getter @Setter private String createdTime;
    @Getter @Setter private String account;

    @Getter @Setter private Map<String,Integer> statistics = new HashMap<>();

    @Getter @Setter private List<Website> websites = new ArrayList<>();
    @Getter @Setter private List<Portrait> portraits = new ArrayList<>();


    public TwitterUser() {
    }

    public Integer getPoints(){
        //Todo: Make this happend...
        int points = 3;
        return points;
    }

    public void addStat(String key,int value){
        statistics.put(key,value);
    }

    public void addPortrait(Portrait portrait){
        portraits.add(portrait);
    }
    public void addWebsite(Website website){
        websites.add(website);
    }

    public static void insertOrUpdate(Object o) {

        TwitterUser user;

        JSONObject json = (JSONObject) o;
        String uri = (String) json.get("uri");

        List<TwitterUser> users = query(
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

        JSONArray portraits = (JSONArray) json.get("portrait");
        JSONArray websites = (JSONArray) json.get("websites");


        user.setUri(uri);
        user.setName((String) json.get("name"));
        user.setLink((String) json.get("link"));
        user.setLocation((String) json.get("location"));
        user.setBio((String) json.get("bio"));
        user.setAccount((String) json.get("account"));

        user.setPortraits(new ArrayList<>());
        for(int i = 0; i < portraits.size(); i++){
            JSONObject portraitObj = (JSONObject) portraits.get(i);
            user.addPortrait(new Portrait(
                (String) portraitObj.get("link"),
                (Long) portraitObj.get("height"),
                (Long) portraitObj.get("width")
            ));
        }

        user.setWebsites(new ArrayList<>());
        for(int i = 0; i < websites.size(); i++){
            JSONObject webObj = (JSONObject) websites.get(i);
            user.addWebsite(new Website(
                    (String) webObj.get("link"),
                    (String) webObj.get("name"),
                    (String) webObj.get("description")
            ));
        }

        user.setCreatedTime((String) json.get("createdTime"));
        user.setStatistics((Map<String, Integer>) json.get("statistics"));

        user.save();
    }

    public static List<TwitterUser> getAll(){
        return query("select * from TwitterUser");
    }

    public static List<TwitterUser> search(String location, String keyword) {
        return query("select * from TwitterUser where bio like '" + keyword + "' and location like '" + location + "'");
    }

}
