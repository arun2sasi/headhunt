package headhunt.app.modules.resultDialog;

import headhunt.schemas.Schema;
import headhunt.schemas.classes.VimeoUser;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResultDialogFx {

	public ResultDialogFx(int rank, Schema user) {
		ResultDialogPresenter.setRankPosition(rank);
		ResultDialogPresenter.setUser((VimeoUser) user);

		Stage stage = new Stage();
		Scene scene = new Scene(new ResultDialogView().getView());

		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("New task");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

	}

}
