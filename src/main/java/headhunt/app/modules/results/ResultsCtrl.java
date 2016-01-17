package headhunt.app.modules.results;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.schemas.Schema;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

//Todo: Make every controller like this...
//Todo: This is main api for the everything else...
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

	public void setTableItems(ObservableList<Schema> schemas) {
		presenter.getResultsTable().setItems(schemas);
	}
}
