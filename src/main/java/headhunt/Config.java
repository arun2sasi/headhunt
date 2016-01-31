package headhunt;

import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

final public class Config {

	final static public class ENV {

		public static final String NAME = System.getProperty("ENV", "production");
		public static final String DB_TYPE;
		public static final Boolean DB_SEED;
		public static final String DB_URL;

		static {
			Properties props = new Properties();
			try {
				props.load(Main.class.getResourceAsStream("/config/env.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			DB_TYPE = props.getProperty(NAME + "." + "db.type");
			DB_SEED = Boolean.parseBoolean(props.getProperty(NAME + "." + "db.seed"));
			DB_URL = getInstallPath() + "/" + props.getProperty(NAME + "." + "db.url");
		}
	}

	final static public class PACKAGE {

		public static final String BUILD_DATE;
		public static final String NAME;
		public static final String VERSION;
		public static final String WEBSITE;
		public static final String LICENSE;
		public static final String DESCRIPTION;
		public static final String AUTHOR;
		public static final String AUTHOR_EMAIL;
		public static final String AUTHOR_URL;
		public static final String AUTHOR_LOCATION;

		static {
			Properties props = new Properties();
			try {
				props.load(Main.class.getResourceAsStream("/config/package.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			BUILD_DATE = props.getProperty("build.date");
			NAME = props.getProperty("name");
			VERSION = props.getProperty("version");
			WEBSITE = props.getProperty("website");
			LICENSE = props.getProperty("license");
			DESCRIPTION = props.getProperty("description");
			AUTHOR = props.getProperty("author");
			AUTHOR_EMAIL = props.getProperty("author.email");
			AUTHOR_URL = props.getProperty("author.url");
			AUTHOR_LOCATION = props.getProperty("author.location");
		}

	}

	public static void setInstallPath(String path) {
		Preferences prefs = Preferences.userNodeForPackage(Config.class);
		prefs.put("installPath", path);
	}

	public static String getInstallPath() {
		Preferences prefs = Preferences.userNodeForPackage(Config.class);
		return prefs.get("installPath", null);
	}
}
