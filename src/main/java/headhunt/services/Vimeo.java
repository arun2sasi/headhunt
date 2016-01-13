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
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class Vimeo {

	private String apiRoot = "https://api.vimeo.com";
	private String token = "96f56eff59f76a764196f8a3a1f9e9d2";

	public static void main(String[] args) {

		Vimeo vimeo = new Vimeo();
		JSONArray resArr = vimeo.getVimeoUsers();

		System.out.println(resArr);
	}

	private JSONArray getVimeoUsers() {

		HttpResponse<String> response = null;
		JSONArray res = null;
		try {
			response = Unirest.get(apiRoot + "/me")
			.header("Content-Type","application/json")
			.header("Authorization","Bearer " + token)
			.asString();

			JSONParser jsonParser = new JSONParser();

			res = (JSONArray) jsonParser.parse('['+response.getBody()+']');

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		System.out.println(response.getBody());

		return res;
	}

}

