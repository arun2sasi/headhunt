package headhunt.fx.setup.views.intro;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

/**
 * Created by urosjarc on 12/30/15.
 */
public class IntroCtrl {
    private IntroPresenter ctrl;
    private FXMLView fxmlView;

    public IntroCtrl(){
        fxmlView = new IntroView();
        ctrl = (IntroPresenter) fxmlView.getPresenter();
    }

    public Parent getView(){
        return fxmlView.getView();
    }

}
