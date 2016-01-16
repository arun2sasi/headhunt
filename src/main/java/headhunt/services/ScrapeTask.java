package headhunt.services;

import javafx.concurrent.Task;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

public class ScrapeTask extends Task {

	@Setter @Getter
	private String name;

	@Setter @Getter private Callback onScrapeSuccess = (param) -> {return null;};
	@Setter @Getter private Callback onScrapeError = (param) -> {return null;};
	@Setter @Getter private Callback onScrapeFail = (param) -> {return null;};

	public ScrapeTask(String name){
		this.name = name;
	}

	@Override
	protected Object call() throws Exception {
		return null;
	}
}
