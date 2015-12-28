package com.urosjarc.headhunt.preloader;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

public class PreloaderModel {

    @PostConstruct
    public void init() {
        System.out.println("NewTaskModel.init()");
    }

}
