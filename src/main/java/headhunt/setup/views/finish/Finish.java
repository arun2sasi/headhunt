package headhunt.setup.views.finish;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

/**
 * Created by urosjarc on 12/30/15.
 */
public class Finish {
    private FinishPresenter ctrl;
    private FXMLView fxmlView;

    public Finish(){
        fxmlView = new FinishView();
        ctrl = (FinishPresenter) fxmlView.getPresenter();
    }

    public Parent getView(){
        return fxmlView.getView();
    }

    public boolean showReadme(){
        return ctrl.readmeCheckBox.isSelected();
    }

    public boolean runApplication(){
        return ctrl.runCheckBox.isSelected();
    }
}
