package headhunt.app.views.scrapers;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

//Todo: Make every partial view like this...

public class ScrapersCtrl {

	private static ScrapersPresenter press;
	private FXMLView view;

	public ScrapersCtrl() {
		view = new ScrapersView();
		press = (ScrapersPresenter) view.getPresenter();
	}

	public Parent getView() {
		return view.getView();
	}

	public static void updateScrapersTable(){
		press.initScraperTable();
	}

}

