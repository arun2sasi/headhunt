package headhunt;

import com.sun.javafx.application.LauncherImpl;
import headhunt.app.App;
import headhunt.preloader.Preloader;
import headhunt.setup.Setup;
import headhunt.setup.SetupModel;
import org.docopt.Docopt;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Object> opts = new Docopt(
            "Description:\n"
            + "  Put description in here...\n"
            + "\n"
            + "Usage:\n"
            + "  headhunt\n"
            + "  headhunt (-h | --help)\n"
            + "  headhunt (-v | --version)\n"
            + "\n"
            + "Options:\n"
            + "  -v --version  Wizard version.\n"
            + "  -h --help     Show this screen.\n"
            + "\n"
        ).withVersion("Headhunt v0.1.0").parse(args);

        if (SetupModel.installFolder() != null) {
            LauncherImpl.launchApplication(App.class, Preloader.class, args);
        } else {
            LauncherImpl.launchApplication(Setup.class,args);
        }
    }
}
