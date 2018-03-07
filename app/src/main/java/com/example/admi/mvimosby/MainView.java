package com.example.admi.mvimosby;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.Observable;

public interface MainView extends MvpView {

    Observable<Integer> askQuestionIntent();

    Observable<Integer> getAnswerIntent();

    void render(MainViewState viewState);
}
