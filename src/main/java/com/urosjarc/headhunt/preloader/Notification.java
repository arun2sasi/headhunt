package com.urosjarc.headhunt.preloader;

import javafx.application.Preloader;

public class Notification
        extends Object
        implements Preloader.PreloaderNotification {

    private String message;
    private Double progress;

    public Notification(String message, Double progress) {
        this.message = message;
        this.progress = progress;
    }

    public String getMessage() {
        return message;
    }

    public Double getProgress() {
        return progress;
    }

}
