package headhunt.wizard;

import headhunt.wizard.views.path.Path;

import javax.annotation.PostConstruct;

public class WizardModel {

    public int viewIndex = 0;
    public Path path;

    @PostConstruct
    public void init() {
        System.out.println("Wizard.init()");
    }

}

