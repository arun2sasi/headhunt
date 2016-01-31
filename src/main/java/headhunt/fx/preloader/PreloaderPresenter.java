package headhunt.fx.preloader;

import headhunt.Config;
import headhunt.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

public class PreloaderPresenter implements Initializable {
    @FXML
    @Getter private ProgressBar progressBar;
	@FXML
	@Getter private Text loadingText;
	@FXML
	private Text titleText;
	@FXML
	private Text infoText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("LoadingDialog init");

		Font titleFont = Font.loadFont(PreloaderPresenter.class.getResourceAsStream("/media/SweetlyBroken.ttf"),100);
		Font infoFont = Font.loadFont(PreloaderPresenter.class.getResourceAsStream("/media/SweetlyBroken.ttf"),40);
		Font loadingFont = Font.loadFont(PreloaderPresenter.class.getResourceAsStream("/media/Technical.ttf"),20);

		titleText.setFont(titleFont);
		loadingText.setFont(loadingFont);
		infoText.setFont(infoFont);
		infoText.setText(Config.PACKAGE.VERSION + "  (" + Config.PACKAGE.BUILD_DATE + ")");

    }

}
