package headhunt.setup;

import headhunt.app.AppModel;
import headhunt.setup.views.finish.Finish;
import headhunt.setup.views.intro.Intro;
import headhunt.setup.views.license.License;
import headhunt.setup.views.path.Path;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.prefs.Preferences;

public class SetupModel {

    public int viewIndex = 0;

    private static Preferences prefs = Preferences.userNodeForPackage(AppModel.class);

    @Setter
    private Path pathModule;
    @Setter @Getter
    private Finish finishModule;
    @Setter
    private License licenseModule;
    @Setter
    private Intro introModule;

    @PostConstruct
    public void init() {
        System.out.println("Wizard.init()");
    }

    public boolean pathExists(){
        return new File(pathModule.getPath()).exists();
    }

    public static String getInstallPath(){
        return prefs.get("installPath",null);
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

            prefs.put("installPath",pathModule.getPath());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

