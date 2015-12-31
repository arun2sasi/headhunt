package headhunt.wizard.views.license;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

/**
 * Created by urosjarc on 12/30/15.
 */
public class License {
    private LicensePresenter ctrl;
    private FXMLView fxmlView;

    public License(){
        fxmlView = new LicenseView();
        ctrl = (LicensePresenter) fxmlView.getPresenter();
    }

    public Parent getView() {
        return fxmlView.getView();
    }

}
