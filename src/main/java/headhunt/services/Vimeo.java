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

public class Vimeo {

	private String api = "https://api.vimeo.com";
	private String acceptHeader = "application/vnd.vimeo.*;version=3.2";
	private String userAgent = "pyvimeo 0.1; (http://developer.vimeo.com/api/docs)";

	private String token = "<token>";
	private String key = "<key>";
	private String secret = "<secret>";

	public static void main(String[] args) {

		Vimeo vimeo = new Vimeo();
		JSONArray resArr = vimeo.getVimeoUsers();

		System.out.println(resArr);

	}

	private JSONArray getVimeoUsers() {

		HttpResponse<String> response = null;
		JSONArray res = null;
		try {
			response = Unirest.post("http://httpbin.org/post")
			.header("accept", "application/json")
			.queryString("apiKey", "123")
			.field("parameter", "value")
			.field("foo", "bar")
			.asString();

			JSONParser jsonParser = new JSONParser();
			res = (JSONArray) jsonParser.parse('['+response.getBody()+']');

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return res;
	}

}

