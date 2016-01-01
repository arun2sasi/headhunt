package headhunt.setup.views.path;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

/**
 * Created by urosjarc on 12/30/15.
 */
public class Path {
    private PathPresenter ctrl;
    private FXMLView fxmlView;

    public Path(){
        fxmlView = new PathView();
        ctrl = (PathPresenter) fxmlView.getPresenter();
    }

    public Parent getView() {
        return fxmlView.getView();
    }

    public String getPath(){
        return ctrl.getDirField().getText();
    }
}
