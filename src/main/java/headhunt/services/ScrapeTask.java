package headhunt.services;

import headhunt.schemas.Schema;
import headhunt.schemas.ScraperSchema;
import javafx.concurrent.Task;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

public class ScrapeTask extends Task {

	@Setter @Getter
	private ScraperSchema scraper;

	@Setter @Getter private Callback onScrapeSuccess = (param) -> {return null;};
	@Setter @Getter private Callback onScrapeError = (param) -> {return null;};
	@Setter @Getter private Callback onScrapeFail = (param) -> {return null;};

	public ScrapeTask(ScraperSchema scraper){
		this.scraper = scraper;
	}

	@Override
	protected Object call() throws Exception {
		return null;
	}
}
