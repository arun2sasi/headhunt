package com.urosjarc.headhunt.preloader;

import com.airhacks.afterburner.views.FXMLView;
import lombok.Getter;

public class PreloaderView extends FXMLView {

    @Getter
    private static PreloaderPresenter ctrl;

    public PreloaderView(String title) {
        PreloaderView.ctrl = (PreloaderPresenter) this.getPresenter();
    }

    public static void setProgress(String text,Double progress) {
        PreloaderView.ctrl.textLabel.setText(text);
        PreloaderView.ctrl.progressBar.setProgress(progress);
    }
}

