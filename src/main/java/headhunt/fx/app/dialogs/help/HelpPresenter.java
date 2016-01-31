package headhunt.fx.app.dialogs.help;

//INJECTING-CHILD
//INJECTING-END

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.web.WebView;
import org.apache.commons.io.IOUtils;
import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpPresenter implements Initializable {

    //INJECTING-NODE
	@FXML private WebView video;
	@FXML private ProgressIndicator progress;
    //INJECTING-END

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("SearchDialog init");

		video.setVisible(false);
		progress.progressProperty().bind(video.getEngine().getLoadWorker().progressProperty());
		video.getEngine().getLoadWorker().stateProperty().addListener( (ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                progress.setVisible(false);
                video.setVisible(true);
            }
        });
    }

	public void initTutorial() {
		//Todo: Make this happend...
		video.getEngine().load(
            "https://www.youtube.com/embed/pPFabRaQI-0?list=RDpPFabRaQI-0"
		);
	}

	public void initDocumentation() {
		try{
			String docsMd = IOUtils.toString(this.getClass().getResourceAsStream("/data/docs.md"));
			String docsHtml = new Markdown4jProcessor().process(docsMd);
			video.getEngine().loadContent(docsHtml);
		} catch (IOException e){
			e.printStackTrace();
		};
	}
}
