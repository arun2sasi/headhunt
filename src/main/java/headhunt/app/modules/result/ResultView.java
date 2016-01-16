package headhunt.app.modules.result;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.schemas.Schema;
import headhunt.schemas.classes.VimeoUser;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResultView extends FXMLView {

    public ResultView(int rank, Schema user) {
        ResultPresenter.setRankPosition(rank);
        ResultPresenter.setUser((VimeoUser) user);

        Stage stage = new Stage();
        Scene scene = new Scene(this.getView());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("New task");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }
}
