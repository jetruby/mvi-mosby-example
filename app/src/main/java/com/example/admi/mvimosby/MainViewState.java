package com.example.admi.mvimosby;

public class MainViewState {

    boolean loading;
    boolean questionShown;
    boolean answerShown;
    String textToShow;
    Throwable error;

    public MainViewState(boolean loading, boolean questionShown, boolean answerShown, String textToShow, Throwable error) {
        this.loading = loading;
        this.questionShown = questionShown;
        this.answerShown = answerShown;
        this.textToShow = textToShow;
        this.error = error;
    }
}
