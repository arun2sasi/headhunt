package headhunt.services;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiScrape {

	public static void main(String[] args) {

		Thread vimeoScraper = new ApiScrape.VimeoUsers();
		vimeoScraper.start();

	}

	public static class VimeoUsers extends Thread {

		private Vimeo vimeo = new Vimeo();

		@Override
		public void run() {

			while (true) try {

				Map<String, Object> res = vimeo.reqUsers();
				int status = (int) res.get("status");

				System.out.println("Status( " + status + " ) => " + res.get("body"));

				if (status != 200) {
                    TimeUnit.MINUTES.sleep(20);
				} else {
					TimeUnit.MINUTES.sleep(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

