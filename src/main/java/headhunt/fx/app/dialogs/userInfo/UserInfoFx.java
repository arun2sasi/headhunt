package headhunt.fx.app.dialogs.userInfo;

import com.airhacks.afterburner.views.FXMLView;
import headhunt.database.schemas.Schema;
import headhunt.database.classes.VimeoUser;
import headhunt.database.records.Portrait;
import headhunt.database.records.Website;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoFx {

	private FXMLView view;
	private UserInfoPresenter press;

	@Setter private VimeoUser user;
	@Setter private int rank;

	public UserInfoFx(int rank, Schema user) {
		this.rank = rank;
		this.user = (VimeoUser) user;

		view = new UserInfoView();
		press = (UserInfoPresenter) this.view.getPresenter();

		init();

		Stage stage = new Stage();
		Scene scene = new Scene(view.getView());

		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("User info");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.show();

	}

	private void init(){
		press.getRank().setText(String.valueOf(rank));
		if(user.getPortraits().size() == 0){
			press.getImage().setImage(new Image(getClass().getResourceAsStream("/media/incognito.jpg")));
		} else {
			Portrait maxPortrait = user.getPortraits().get(0);
			for(Portrait portrait: user.getPortraits()){
				if(maxPortrait.getHeight() < portrait.getHeight()){
					maxPortrait = portrait;
				}
			}
			press.getImage().setImage(new Image(maxPortrait.getLink()));
		}
		press.getName().setText(user.getName());
		press.getPoints().setText(String.valueOf(user.getPoints()));
		press.getLocation().setText(user.getLocation());
		press.getAccountType().setText(user.getAccount());
		press.getUri().setText(user.getUri());
		press.getBio().setText(user.getBio());
		press.getLink().setText(user.getLink());

		//Set websites
		updateWebsiteList(user.getWebsites());

		//Set activities
		updateActivityTable(user.getStatistics());

		//Set userPagebutton

	}

	private void updateWebsiteList(List<Website> websites) {
		//Set websites
		ObservableList<Website> websitesObList = FXCollections.observableArrayList(websites);
		press.getWebsites().setCellFactory(new Callback<ListView<Website>, ListCell<Website>>() {
			@Override
			public ListCell<Website> call(ListView<Website> p) {

				ListCell<Website> cell = new ListCell<Website>() {

					@Override
					protected void updateItem(Website t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							String text = "";
							if (t.getName() != null) {
								text += t.getName();
							}
							if (t.getDescription() != null) {
								text += " : " + t.getDescription();
							}
							if (t.getLink() != null) {
								text += " : " + t.getLink();
							}

							setText(text);
						}

					}

				};
				return cell;
			}
		});
		press.getWebsites().setItems(websitesObList);
	}

	private void updateActivityTable(Map<String, Integer> map) {

		press.getActivityName().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
				return new SimpleStringProperty(p.getValue().getKey());
			}
		});

		press.getActivityValue().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer>, ObservableValue<Integer>>() {

			@Override
			public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer> p) {
				return new SimpleObjectProperty<Integer>(p.getValue().getValue());
			}
		});

		Map<String, Integer> newMap = new HashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {

			Object value = (Object) entry.getValue();
			if (!value.toString().equals("0")) newMap.put(entry.getKey(), new Integer(value.toString()));
		}

		ObservableList<Map.Entry<String, Integer>> items = FXCollections.observableArrayList(newMap.entrySet());

		press.getActivityTable().setItems(items);
	}
}
