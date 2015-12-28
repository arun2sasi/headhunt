package com.urosjarc.headhunt;

import com.sun.javafx.application.LauncherImpl;
import com.urosjarc.headhunt.app.App;
import com.urosjarc.headhunt.preloader.PreloaderView;
import org.docopt.Docopt;

import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));

        Map<String, Object> opts = new Docopt(
            "Description:\n"
            + "  Put description in here...\n"
            + "\n"
            + "Usage:\n"
            + "  headhunt\n"
            + "  headhunt (-h | --help)\n"
            + "  headhunt (-v | --version)\n"
            + "\n"
            + "Options:\n"
            + "  -v --version  App version.\n"
            + "  -h --help     Show this screen.\n"
            + "\n"
        ).withVersion("Headhunt v0.1.0").parse(args);

        LauncherImpl.launchApplication(App.class, PreloaderView.class, args);
    }
}
