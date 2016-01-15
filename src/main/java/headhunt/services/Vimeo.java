package headhunt.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.cell.ProgressBarTreeTableCell;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by urosjarc on 14.1.2016.
 */
public class Vimeo {

	private String apiRoot = "https://api.vimeo.com";
	private String apiVersion = "application/vnd.vimeo.*+json;version=3.2";
	private String contentType = "application/json";
	private String token = "96f56eff59f76a764196f8a3a1f9e9d2";

	private int page = 2;
	private int itemsPerPage = 50;
	@Getter
	private String query = "abc";
	private String sortBy = "relevant";
	private String sortDirection = "desc";

	public Map<String, Object> reqUsers() {

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

	public Task<Void> scrapeVimeoUsers() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				updateMessage("2000 milliseconds");
				while (true) try {

					Map<String, Object> res = reqUsers();
					int status = (int) res.get("status");

					if (status != 200) {
						System.out.println(status);
						TimeUnit.MINUTES.sleep(20);
					} else {
						TimeUnit.MINUTES.sleep(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

	}

	public static void main(String[] args) throws InterruptedException {

		ProgressBar progressBar = new ProgressBar();

		ProgressIndicator pi = null;
		new Thread() {
			public void run() {
				System.out.println("Thread...");
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(new Random().nextInt(1000));
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					final double progress = i * 0.05;
					Platform.runLater(new Runnable() {
						public void run() {
							pi.setProgress(progress);
						}
					});
				}
			}
		}.start();


	}
}
