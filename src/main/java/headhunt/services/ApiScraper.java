package headhunt.services;

import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiScraper extends Thread {

	public static void main(String[] args) {

		ApiScraper vimeoScraper = new ApiScraper.VimeoUsers("Name");

        vimeoScraper.setOnSuccess(new Callback(){
			@Override
			public Void call(Object body){

				System.out.println("Success...");
				System.out.println(body);

				return null;
			}
		});

		vimeoScraper.start();
	}

	public Object getProgress(){
		return 322;
	}

	private Callback<Object,Void> onSuccess;

	public void setOnSuccess(Callback<Object,Void> callback){
		onSuccess = callback;
	}

	public static class VimeoUsers extends ApiScraper {

		public VimeoUsers(String name){
			setName(name);
		}

		private Vimeo vimeo = new Vimeo();

		@Override
		public void run() {

			while (true) try {

				Map<String, Object> res = vimeo.reqUsers();
				int status = (int) res.get("status");

				if (status != 200) {
					TimeUnit.MINUTES.sleep(20);
				} else {
					super.onSuccess.call(res.get("body"));
					TimeUnit.MINUTES.sleep(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

