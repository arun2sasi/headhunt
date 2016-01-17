package headhunt.app.modules.scrapers;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.setup.views.finish.FinishPresenter;
import headhunt.setup.views.finish.FinishView;
import javafx.scene.Parent;

//Todo: Make every partial view like this...

public class ScrapersCtrl {

	private ScrapersPresenter presenter;
	private FXMLView view;

	public ScrapersCtrl() {
		view = new ScrapersView();
		presenter = (ScrapersPresenter) view.getPresenter();
	}

	public Parent getView() {
		return view.getView();
	}

}

