package headhunt.schemas.classes;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import headhunt.app.AppModel;
import headhunt.schemas.Schema;
import headhunt.schemas.records.Portrait;
import headhunt.schemas.records.Website;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VimeoUser extends Schema {

    @Getter public static StringProperty countProperty = new SimpleStringProperty();

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

    public VimeoUser() {}

	public static void updateCountProperty(){
		ODocument count = (ODocument) query("SELECT COUNT(*) FROM VimeoUser").get(0);

		countProperty.setValue("Class items: " + count.field("COUNT"));
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

        VimeoUser user;

        JSONObject json = (JSONObject) o;
        String uri = (String) json.get("uri");

        List<VimeoUser> users = (List<VimeoUser>) query(
            "select * from VimeoUser where uri = '" + uri + "'"
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
            user = new VimeoUser();
        }

        user.setUri(uri);
        user.setName((String) json.get("name"));
        user.setLink((String) json.get("link"));
        user.setLocation((String) json.get("location"));
        user.setBio((String) json.get("bio"));
        user.setAccount((String) json.get("account"));
		user.setCreatedTime((String) json.get("createdTime"));
		user.setStatistics((Map<String, Integer>) json.get("statistics"));

		JSONArray portraits = (JSONArray) json.get("portrait");
        user.setPortraits(new ArrayList<>());
        for(int i = 0; i < portraits.size(); i++){
            JSONObject portraitObj = (JSONObject) portraits.get(i);
            user.addPortrait(new Portrait(
                (String) portraitObj.get("link"),
                (Long) portraitObj.get("height"),
                (Long) portraitObj.get("width")
            ));
        }

		JSONArray websites = (JSONArray) json.get("websites");
        user.setWebsites(new ArrayList<>());
        for(int i = 0; i < websites.size(); i++){
            JSONObject webObj = (JSONObject) websites.get(i);
            user.addWebsite(new Website(
                    (String) webObj.get("link"),
                    (String) webObj.get("name"),
                    (String) webObj.get("description")
            ));
        }

        user.save();

    }

    public static List<VimeoUser> getAll(){
        return (List<VimeoUser>) query("select * from VimeoUser");
    }

    public static List<VimeoUser> search(String location, String keyword) {
        return (List<VimeoUser>) query("select * from VimeoUser where bio like '" + keyword + "' and location like '" + location + "'");
    }

	public static void insertOrUpdateAllReqUsers(Object vimeoReq){

		JSONArray dataArrJson = (JSONArray) ((JSONObject) vimeoReq).get("data");

		for(Object dataArrObj: dataArrJson){
			JSONObject userJson = (JSONObject) dataArrObj;

			/**
			 * STATISTICS
			 */
			JSONObject statsJson = (JSONObject) ((JSONObject) userJson.get("metadata")).get("connections");
			Map<String,Integer> statistics = new HashMap<>();
			statistics.put("shared", ((Long) ((JSONObject) statsJson.get("shared")).get("total")).intValue());
			statistics.put("albums",((Long) ((JSONObject) statsJson.get("albums")).get("total")).intValue());
			statistics.put("followers",((Long) ((JSONObject) statsJson.get("followers")).get("total")).intValue());
			statistics.put("channels",((Long) ((JSONObject) statsJson.get("channels")).get("total")).intValue());
			statistics.put("following",((Long) ((JSONObject) statsJson.get("following")).get("total")).intValue());
			statistics.put("groups",((Long) ((JSONObject) statsJson.get("groups")).get("total")).intValue());
			statistics.put("videos",((Long) ((JSONObject) statsJson.get("videos")).get("total")).intValue());
			statistics.put("pictures",((Long) ((JSONObject) statsJson.get("pictures")).get("total")).intValue());
			statistics.put("likes",((Long) ((JSONObject) statsJson.get("likes")).get("total")).intValue());

			/**
			 * WEBSITES
			 */
			List<Website> websites = new ArrayList<>();
			for(Object websiteObj: (JSONArray) userJson.get("websites")){
				JSONObject websiteJson = (JSONObject) websiteObj;
				websites.add(new Website(
                    (String) websiteJson.get("link"),
                    (String) websiteJson.get("name"),
                    (String) websiteJson.get("description")
				));
			}

			/**
			 * PORTRAITS
			 */
			List<Portrait> portraits = new ArrayList<>();
			JSONObject picturesJson = (JSONObject) userJson.get("pictures");
			if(picturesJson != null){
				for(Object portraitObj: (JSONArray) picturesJson.get("sizes")){
					JSONObject portraitJson = (JSONObject) portraitObj;
					portraits.add(new Portrait(
                        (String) portraitJson.get("link"),
                        ((Long) portraitJson.get("height")).intValue(),
                        ((Long) portraitJson.get("width")).intValue()
					));
				}
			}
			/**
			 * SAVE OR UPDATE OBJECT
			 */
			List<VimeoUser> existingUsers = (List<VimeoUser>) query(
                "select * from VimeoUser where uri = '" + userJson.get("uri") + "'"
			);

			//Update
			if(existingUsers.size() == 1){
				VimeoUser user = existingUsers.get(0);

            //Create
			} else if(existingUsers.size() == 0){
				new VimeoUser(
                    (String) userJson.get("uri"),
                    (String) userJson.get("name"),
                    (String) userJson.get("link"),
                    (String) userJson.get("location"),
                    (String) userJson.get("bio"),
                    (String) userJson.get("time"),
                    (String) userJson.get("account"),
                    statistics,
                    websites,
                    portraits
				).save();

			}

		}

	}

}
