package headhunt;

import com.sun.javafx.application.LauncherImpl;
import headhunt.app.AppFx;
import headhunt.preloader.PreloaderFx;
import headhunt.setup.SetupFx;
import headhunt.setup.SetupModel;
import org.docopt.Docopt;
import com.jcabi.manifests.Manifests;

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
        ).withVersion("Headhunt " + Manifests.read("Implementation-Version")).parse(args);

	System.out.println(Manifests.read("Implementation-Version"));

        if (SetupModel.getInstallPath() != null) {
            LauncherImpl.launchApplication(AppFx.class, PreloaderFx.class, args);
        } else {
            LauncherImpl.launchApplication(SetupFx.class,args);
        }
    }
}
