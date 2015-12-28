package com.urosjarc.headhunt;

import com.sun.javafx.application.LauncherImpl;
import com.urosjarc.headhunt.app.App;
import com.urosjarc.headhunt.preloader.PreloaderView;

public class Main {
    public static void main(String[] args) {
        LauncherImpl.launchApplication(App.class, PreloaderView.class, args);
    }
}
