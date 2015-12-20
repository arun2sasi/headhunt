package com.urosjarc.headhunt.modules.loadingDialog;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

public class LoadingDialogModel {

    @Getter @Setter private boolean shouldClose = false;
    @Setter @Getter private Task<Void> task;

    @PostConstruct
    public void init() {
        System.out.println("NewTaskModel.init()");
    }

}
