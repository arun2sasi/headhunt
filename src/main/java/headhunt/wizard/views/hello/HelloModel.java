package headhunt.wizard.views.hello;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

public class HelloModel {

    @PostConstruct
    public void init() {
        System.out.println("NewTaskModel.init()");
    }

}
