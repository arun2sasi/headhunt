package headhunt;

import com.sun.javafx.application.LauncherImpl;
import headhunt.app.AppFx;
import headhunt.preloader.PreloaderFx;
import headhunt.setup.SetupFx;
import lombok.Getter;
import org.docopt.Docopt;
import com.jcabi.manifests.Manifests;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

public class Main {


	final public static String VERSION =  Manifests.read("Implementation-Version");
	final public static String TITLE = Manifests.read("Implementation-Title");
	final public static String BUILD_DATE = Manifests.read("Build-Date");

	@Getter private static Properties props = new Properties();
	private static Preferences prefs = Preferences.userNodeForPackage(Main.class);

	public static String getInstallPath(){
		return prefs.get("installPath",null);
	}
	public static void setInstallPath(String path){
		prefs.put("installPath",path);
	}

	public static void main(String[] args) {

		try {
			props.load(
                Main.class.getResourceAsStream("/config.properties")
            );
		} catch (IOException e) {
			e.printStackTrace();
		}

		String appBinTitle = TITLE.toLowerCase();

        Map<String, Object> opts = new Docopt(
            "Description:\n"
            + String.format("  %s\n",props.getProperty("description"))
            + "\n"
            + "Usage:\n"
            + String.format("  %s\n", appBinTitle)
			+ String.format("  %s (-h | --help)\n",appBinTitle)
            + String.format("  %s (-v | --version)\n",appBinTitle)
            + "\n"
            + "Options:\n"
            + "  -v --version  Show version.\n"
            + "  -h --help     Show this screen.\n"
            + "\n"
        ).withVersion(TITLE + " " + VERSION).parse(args);

        if (getInstallPath() != null) {
            LauncherImpl.launchApplication(AppFx.class, PreloaderFx.class, args);
        } else {
            LauncherImpl.launchApplication(SetupFx.class,args);
        }
    }
}
