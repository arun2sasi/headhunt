package headhunt.services;

import org.json.simple.JSONObject;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiScrape {

	public static ScrapeTask vimeoUsers(String taskName,String apiToken,String query,int page){
        return new ScrapeTask(taskName) {

			private VimeoApi vimeoApi = new VimeoApi(apiToken);

			@Override
			protected Object call() throws Exception {

				updateProgress(-1, 0);

				//Todo: Make this interact with table.
				updateTitle("Title");
				updateMessage("Message");
				updateValue("Value");

				while (true) try {

					Map<String, Object> res = vimeoApi.reqUsers(query,page);

					int status = (int) res.get("status");
					JSONObject body = (JSONObject) res.get("body");

					if (status != 200) {
						getOnScrapeFail().call(body);
						TimeUnit.MINUTES.sleep(20);
					} else {

						long lastPage = (long) body.get("total") / (long) body.get("per_page");
						long page = (long) body.get("page");

						updateProgress(page, lastPage);

						getOnScrapeSuccess().call(body);
						TimeUnit.MINUTES.sleep(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
					getOnScrapeError().call(e);
				}
			}
		};
	}
}
