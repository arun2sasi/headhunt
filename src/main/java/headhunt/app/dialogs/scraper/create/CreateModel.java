package headhunt.app.dialogs.scraper.create;

import javafx.scene.control.MenuItem;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

/**
 * Created by urosjarc on 22.1.2016.
 */
public class CreateModel {

	@Getter private MenuItem vimeoUsersItem = new MenuItem("Vimeo users");
	@Setter @Getter private MenuItem selectedItem;

	@PostConstruct
	public void init() {
		System.out.println("NewTaskModel.init()");
	}
}
