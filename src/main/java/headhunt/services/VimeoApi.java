package headhunt.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class VimeoApi {

	static private Preferences preferences = Preferences.userNodeForPackage(VimeoApi.class);

	static public int getSleepTimeOnFail(){
		preferences = Preferences.userNodeForPackage(VimeoApi.class);
		return preferences.getInt("waitTimeOnFail",20);
	}
	static public int getSleepTimeOnSuccess(){
		preferences = Preferences.userNodeForPackage(VimeoApi.class);
		return preferences.getInt("waitTimeOnSuccess",1);
	}
	static public void setSleepTimeOnFail(int minutes){
		preferences.putInt("waitTimeOnFail",minutes);
	}
	static public void setSleepTimeOnSuccess(int minutes){ preferences.putInt("waitTimeOnSuccess",minutes); }

	final private String apiRoot = "https://api.vimeo.com";
	final private String apiVersion = "application/vnd.vimeo.*+json;version=3.2";
	final private String contentType = "application/json";
	final private String sortBy = "alphabetical";
	final private String sortDirection = "desc";
	final private int itemsPerPage = 50;

	private String token;

	public VimeoApi(String token){
		this.token = token;
	}

	public Map<String, Object> reqUsers(String query, int page) {

		Map<String,Object> resMap = new HashMap();

		HttpResponse<String> res = null;
		JSONObject resJson = null;

		try {
			res = Unirest.get(apiRoot + "/users")
			.header("Content-Type",contentType)
			.header("Authorization","Bearer " + token)
			.header("Accept",apiVersion)
			.queryString("per_page",Integer.toString(itemsPerPage))
			.queryString("sort",sortBy)
			//Important
			.queryString("query",query)
			.queryString("page", Integer.toString(page))
			.queryString("direction",sortDirection)
			//---------
			.asString();

			JSONParser jsonParser = new JSONParser();

			resJson = (JSONObject) jsonParser.parse(res.getBody());
			int status = res.getStatus();

			resMap.put("body",resJson);
			resMap.put("status", res.getStatus());

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return resMap;
	}

}
