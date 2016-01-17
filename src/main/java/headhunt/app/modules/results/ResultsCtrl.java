package headhunt.app.modules.results;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

//Todo: Make every partial view like this...

public class ResultsCtrl {

	private ResultsPresenter presenter;
	private FXMLView view;

	public ResultsCtrl() {
		view = new ResultsView();
		presenter = (ResultsPresenter) view.getPresenter();
	}

	public Parent getView() {
		return view.getView();
	}

}
