package headhunt.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import headhunt.database.classes.VimeoUsersScraper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiScrape {

	public static ScrapeTask vimeoUsers(VimeoUsersScraper vimeoUsersScraper) {

		String apiToken = vimeoUsersScraper.getToken();
		String query = vimeoUsersScraper.getQuery();
		final int[] page = {vimeoUsersScraper.getPage()};

		return new ScrapeTask(vimeoUsersScraper) {

			private VimeoApi vimeoApi = new VimeoApi(apiToken);

			@Override
			protected Object call() throws InterruptedException {

				updateProgress(-1, 0);
				updateMessage("Init...");

				while (true) try {

					Map<String, Object> res = null;

                    res = vimeoApi.reqUsers(query, page[0]);

					int status = (int) res.get("status");
					JSONObject body = (JSONObject) res.get("body");

					if (status != 200) {
						getOnScrapeFail().call(body);
						updateMessage(
						"FAIL => " + body.toString()
						);
						TimeUnit.MINUTES.sleep(VimeoApi.getSleepTimeOnFail());
					} else {

						long lastPage = (long) body.get("total") / (long) body.get("per_page");

						updateProgress(page[0], lastPage);
						getOnScrapeSuccess().call(body);

						updateMessage(
						String.format("Left: %.3f ", 100 - ((double) page[0] / lastPage) * 100) + "%" +
						String.format(" => %.3f ",
						(VimeoApi.getSleepTimeOnSuccess() * (lastPage - page[0]))
						/
						(double) (60 * 24)
						) + " days"
						);

						page[0]++;

						if (page[0] > lastPage) {
							page[0] = 1;
						}

						vimeoUsersScraper.setPage(page[0]);
						vimeoUsersScraper.save();

						TimeUnit.MINUTES.sleep(VimeoApi.getSleepTimeOnSuccess());
					}
				} catch (UnirestException e) {
					e.printStackTrace();
					updateMessage(
                        "FAIL => " + e.getLocalizedMessage()
					);
					getOnScrapeFail().call(e);
					TimeUnit.MINUTES.sleep(VimeoApi.getSleepTimeOnError());
				} catch (ParseException e) {
					e.printStackTrace();
					updateMessage(
                        "FAIL => " + e.getLocalizedMessage()
					);
					getOnScrapeFail().call(e);
					TimeUnit.MINUTES.sleep(VimeoApi.getSleepTimeOnError());
				}
			}
		};
	}
}
