package headhunt.setup;

import headhunt.Main;
import headhunt.app.AppModel;
import headhunt.setup.views.finish.FinishCtrl;
import headhunt.setup.views.intro.IntroCtrl;
import headhunt.setup.views.license.LicenseCtrl;
import headhunt.setup.views.path.PathCtrl;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.prefs.Preferences;

public class SetupModel {

    public int viewIndex = 0;

    @Setter
    private PathCtrl pathModule;
    @Setter @Getter
    private FinishCtrl finishModule;
    @Setter
    private LicenseCtrl licenseModule;
    @Setter
    private IntroCtrl introModule;

    @PostConstruct
    public void init() {
        System.out.println("Wizard.init()");
    }

    public boolean pathExists(){
        return new File(pathModule.getPath()).exists();
    }

    public boolean userAgreeWithTerms(){
        return licenseModule.userChoice();
    }

    public void initFinish() {
        try{
            File installPath = new File(pathModule.getPath());
            installPath.setExecutable(true, false);
            installPath.setReadable(true, false);
            installPath.setWritable(true, false);
            installPath.mkdir();

			Main.setInstallPath(pathModule.getPath());
		} catch (Exception e){
            e.printStackTrace();
        }
    }
}

