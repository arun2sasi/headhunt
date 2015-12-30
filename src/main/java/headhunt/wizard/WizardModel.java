package headhunt.wizard;

import javax.annotation.PostConstruct;

public class WizardModel {

    public int viewIndex = 0;

    @PostConstruct
    public void init() {
        System.out.println("Wizard.init()");
    }

}

