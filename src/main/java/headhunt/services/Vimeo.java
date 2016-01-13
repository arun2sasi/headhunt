package headhunt.services;

/*
 if self.callsRemaining <= 1:
 # Todo: make sleep function base on the x-ratelimit
 for i in range(10, -1, 0):
 msgCallback('Reseting X-RateLimit: ' + i + ' *sec')
 time.sleep(1)
 self.callsRemaining = self.callsLimit

 calls = 0

 while True:
 try:

 # Todo: Callculate slep base on time reset and calls remaining
 time.sleep(random.randint(60 * 2, 60 * 5))

 response = self.api.get(
 '/users?page={page}&per_page={per_page}&query={query}&sort={sort}&direction={direction}'.format(
 page=self.lastPage,
 per_page=self.itemsPerPage,
 query=self.lastQueryWord,
 sort=self.sortBy,
 direction=self.sortDirection
 )
 )
 calls += 1

 # Todo: if response.headers.x-ratelimit:
 # Todo: apiClient.callsRemaining = response.headers.x-ratelimit.callsremaining
 # Todo: apiClient.resetTime = response.headers.x-ratelimit.resettime
 # Todo: apiscraper.callsLimit = response.headers.x-ratelimit.callslimit

 if response.status_code != 200:
 msgCallback('Status {}: *{}'.format(response.status_code, calls))

 for i in range(20):
 msgCallback('{}* status {}: try again after ~ {} *min'.format(
 calls,
 response.status_code,
 20-i
 ))
 time.sleep(60)

 else:
 return response.json()

 except requests.exceptions.Timeout:
 msgCallback('Req. timeout: *{}'.format(calls))
*/

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class Vimeo {

	private String apiRoot = "https://api.vimeo.com";
	private String apiVersion = "application/vnd.vimeo.*+json;version=3.2";
	private String contentType = "application/json";
	private String token = "96f56eff59f76a764196f8a3a1f9e9d2";
	private int page = 2;
	private int itemsPerPage = 50;
	private String query = "abc";
	private String sortBy = "relevant";
	private String sortDirection = "desc";

	private int callsRemain = 100;
	private int callsLimit = 100;

	public static void main(String[] args) {

		Vimeo vimeo = new Vimeo();

		for(int i = 0; i < 10; i++){
			JSONObject res = vimeo.getVimeoUsers();
			System.out.println(i + " = " + res);
		}
	}

	public JSONObject getVimeoUsers(){

		int calls = 0;

		if(callsRemain <= 1){
			for(int i=10; i>-1;i--){
				System.out.println("Reseting X-RateLimit: " + i + " *sec");
				//Todo: Thread sleep for some time.
			}
			callsRemain = callsLimit;
		}

		while(true) try {
			//Todo: Thread sleep for random.randint(60*2,60*5)

			Map<String, Object> res = reqUsers();
			calls += 1;

			int status = (int) res.get("status");

			if ((int) res.get("status") != 200) {
				for(int i=0;i<20;i++){
					System.out.printf("%d* status %d: try again after ~ %d *min", calls, status, 20-i);
					//Todo: Thread sleep for 60 sec.
				}
			} else {
				return (JSONObject) res.get("body");
			}
		} catch (Exception e){
			//Todo: This could be request timeout....
			e.printStackTrace();
		}

	}

	private Map<String, Object> reqUsers() {

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

