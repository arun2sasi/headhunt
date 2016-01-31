package headhunt.fx.app.dialogs.about;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import headhunt.Config;
import headhunt.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AboutPresenter implements Initializable {

    //INJECTING-NODE
	@FXML private Text name;
	@FXML private Text created;
	@FXML private Text description;
	@FXML private Text version;
	@FXML private Text licence;
	@FXML private Text creator;
	@FXML private Text title;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

		Font titleFont = Font.loadFont(this.getClass().getResourceAsStream("/media/SweetlyBroken.ttf"),100);

		title.setFont(titleFont);

        version.setText("v" + Config.PACKAGE.VERSION);
        title.setText(Config.PACKAGE.NAME);
        created.setText(Config.PACKAGE.BUILD_DATE);
		name.setText(Config.PACKAGE.NAME);
		description.setText(Config.PACKAGE.DESCRIPTION.replaceAll("\n",""));
		licence.setText(Config.PACKAGE.LICENSE);
		creator.setText(
			Config.PACKAGE.AUTHOR + "\n" +
            Config.PACKAGE.AUTHOR_URL + "\n" +
            Config.PACKAGE.AUTHOR_EMAIL + "\n" +
            Config.PACKAGE.AUTHOR_LOCATION + "\n"
		);
    }
}
