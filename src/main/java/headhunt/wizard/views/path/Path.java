package headhunt.wizard.views.path;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.wizard.views.intro.IntroPresenter;
import headhunt.wizard.views.intro.IntroView;
import javafx.scene.Parent;
import lombok.Getter;

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
