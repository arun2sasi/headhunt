package headhunt.app.dialogs.settings;

import headhunt.services.VimeoApi;
import lombok.Getter;

import javax.annotation.PostConstruct;
import java.util.prefs.Preferences;

/**
 * Created by urosjarc on 22.1.2016.
 */
public class SettingsModel {

	@PostConstruct
	public void init() {
		System.out.println("NewTaskModel.init()");
	}
}
