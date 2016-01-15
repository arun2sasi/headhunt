package headhunt.services;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

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

	public ObservableValue<Object> getProgress(){
		return new ObservableValue<Object>() {
			@Override
			public void addListener(InvalidationListener listener) {

			}

			@Override
			public void removeListener(InvalidationListener listener) {

			}

			@Override
			public void addListener(ChangeListener<? super Object> listener) {

			}

			@Override
			public void removeListener(ChangeListener<? super Object> listener) {

			}

			@Override
			public Object getValue() {
				return null;
			}
		};
	}

	public String getInfo(){
		return "Info";
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
					//super.onSuccess.call(res.get("body"));
					TimeUnit.MINUTES.sleep(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

