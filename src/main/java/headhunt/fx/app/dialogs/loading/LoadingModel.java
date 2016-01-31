package headhunt.fx.app.dialogs.loading;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

public class LoadingModel {

    @Setter @Getter private Task<Void> task;

    @PostConstruct
    public void init() {
        System.out.println("NewTaskModel.init()");
    }

}
