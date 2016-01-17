package headhunt.app.dialogs.userInfo;

import headhunt.schemas.Schema;
import headhunt.schemas.classes.VimeoUser;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserInfoFx {

	public UserInfoFx(int rank, Schema user) {
		UserInfoPresenter.setRankPosition(rank);
		UserInfoPresenter.setUser((VimeoUser) user);

		Stage stage = new Stage();
		Scene scene = new Scene(new UserInfoView().getView());

		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("New task");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

	}

}
