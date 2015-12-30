package headhunt.wizard.views.intro;

import javafx.stage.Stage;
import org.omg.CORBA.INV_FLAG;

/**
 * Created by urosjarc on 12/30/15.
 */
public class Intro {
    private IntroPresenter ctrl;
    private IntroView view;

    public Intro(){
        view = new IntroView();
        ctrl = (IntroPresenter) view.getPresenter();
    }

    public hide(){
        //Todo: Hide scene...
    }
}
