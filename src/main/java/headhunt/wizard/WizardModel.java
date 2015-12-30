package headhunt.wizard;

import javax.annotation.PostConstruct;

public class WizardModel {

    @PostConstruct
    public void init() {
        System.out.println("WizardModel.init()");
    }

}

