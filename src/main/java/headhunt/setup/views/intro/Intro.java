package headhunt.setup.views.intro;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

/**
 * Created by urosjarc on 12/30/15.
 */
public class Intro {
    private IntroPresenter ctrl;
    private FXMLView fxmlView;

    public Intro(){
        fxmlView = new IntroView();
        ctrl = (IntroPresenter) fxmlView.getPresenter();
    }

    public Parent getView(){
        return fxmlView.getView();
    }

}
