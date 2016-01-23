package headhunt.services;

import headhunt.schemas.classes.VimeoUsersScraper;
import org.json.simple.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiScrape {

	public static ScrapeTask vimeoUsers(VimeoUsersScraper vimeoUsersScraper){

		String apiToken = vimeoUsersScraper.getToken();
		String query = vimeoUsersScraper.getQuery();
		int page = vimeoUsersScraper.getPage();

        return new ScrapeTask(vimeoUsersScraper) {

			private VimeoApi vimeoApi = new VimeoApi(apiToken);

			@Override
			protected Object call() throws Exception {

				updateProgress(-1, 0);
				updateMessage("Init...");

				while (true) try {

					Map<String, Object> res = vimeoApi.reqUsers(query,page);

					int status = (int) res.get("status");
					JSONObject body = (JSONObject) res.get("body");

					if (status != 200) {
						getOnScrapeFail().call(body);
						updateMessage(body.toString());
						TimeUnit.MINUTES.sleep(VimeoApi.getSleepTimeOnFail());
					} else {

						long lastPage = (long) body.get("total") / (long) body.get("per_page");
						long page = (long) body.get("page");

						updateProgress(page, lastPage);
						getOnScrapeSuccess().call(body);

						updateMessage(
                            String.format("Left: %.3f ", 100 - ((double) page / lastPage ) * 100) + "%" +
                            String.format(" => %.3f ",
                                (VimeoApi.getSleepTimeOnSuccess() * (lastPage - page))
									/
								(double) (60 * 24)
							) + " days"
						);

						TimeUnit.MINUTES.sleep(VimeoApi.getSleepTimeOnSuccess());
					}
				} catch (Exception e) {
					e.printStackTrace();
					getOnScrapeError().call(e);
				}
			}
		};
	}
}
