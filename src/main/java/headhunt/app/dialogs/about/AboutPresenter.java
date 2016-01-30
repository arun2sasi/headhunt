package headhunt.app.dialogs.about;

//INJECTING-CHILD
//INJECTING-END

import com.airhacks.afterburner.injection.Injector;
import com.jcabi.manifests.Manifests;
import headhunt.preloader.PreloaderPresenter;
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
	@FXML private Button okButton;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

		Font titleFont = Font.loadFont(this.getClass().getResourceAsStream("/media/SweetlyBroken.ttf"),100);

		title.setFont(titleFont);

		try{
			version.setText("v" + Manifests.read("Implementation-Version"));
			title.setText(Manifests.read("Implementation-Name"));
			created.setText(Manifests.read("Implementation-Date"));
		} catch (IllegalArgumentException e){
			SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
			title.setText("Headhunt");
			created.setText(date.format(Calendar.getInstance().getTime()));
			version.setText("v0.0.1");
		}

		name.setText(title.getText());
		description.setText(
			"Headhunt application is API users scraper which implements custom and optimized" +
			" search queries which find the right person for your job!"
		);
		licence.setText("GNU General Public License v3.0\nCopyright © 2007 Free Software Foundation");
		creator.setText("Uroš Jarc\nhttps://github.com/urosjarc\nSlovenia, Ljubljana");
    }

	public void ok(){
		((Stage) okButton.getScene().getWindow()).close();
		Injector.forgetAll();
	}
}
