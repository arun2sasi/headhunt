package com.urosjarc.headhunt.preloader;

import javax.annotation.PostConstruct;

public class PreloaderModel {

    @PostConstruct
    public void init() {
        System.out.println("NewTaskModel.init()");
    }

}
