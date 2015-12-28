package com.urosjarc.headhunt.app.modules.loadDialog;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

public class LoadDialogModel {

    @Setter @Getter private Task<Void> task;

    @PostConstruct
    public void init() {
        System.out.println("NewTaskModel.init()");
    }

}
