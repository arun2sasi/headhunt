package headhunt.fx.setup.views.finish;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

/**
 * Created by urosjarc on 12/30/15.
 */
public class FinishCtrl {
    private FinishPresenter ctrl;
    private FXMLView fxmlView;

    public FinishCtrl(){
        fxmlView = new FinishView();
        ctrl = (FinishPresenter) fxmlView.getPresenter();
    }

    public Parent getView(){
        return fxmlView.getView();
    }

    public boolean showReadme(){
        return ctrl.readmeCheckBox.isSelected();
    }
	public boolean showTutorial(){
		return ctrl.tutorialCheckBox.isSelected();
	}

}
