package com.example.admi.mvimosby;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends MviBasePresenter<MainView, MainViewState> {

    DataSource dataSource;

    public MainPresenter() {
        this.dataSource = new DataSource();
    }

    @Override
    protected void bindIntents() {
        Observable<PartialMainState> askQuestion = intent(MainView::askQuestionIntent)
                .switchMap(questionId -> dataSource.askQuestion(questionId)
                        .map(question -> (PartialMainState) new PartialMainState.GotQuestion(question))
                        .startWith(new PartialMainState.Loading())
                        .onErrorReturn(error -> new PartialMainState.Error(error))
                        .subscribeOn(Schedulers.io()));

        Observable<PartialMainState> getAnswer = intent(MainView::getAnswerIntent)
                .switchMap(questionId -> dataSource.getAnswer(questionId)
                        .map(answer -> (PartialMainState) new PartialMainState.GotAnswer(answer))
                        .startWith(new PartialMainState.Loading())
                        .onErrorReturn(error -> new PartialMainState.Error(error))
                        .subscribeOn(Schedulers.io()));

        MainViewState initialState = new MainViewState(false, false, false, "Ask Your question", null);
        Observable<PartialMainState> allIntents = Observable.merge(askQuestion, getAnswer)
                .observeOn(AndroidSchedulers.mainThread());

        subscribeViewState(allIntents.scan(initialState, this::viewStateReducer), MainView::render);
    }

    MainViewState viewStateReducer(MainViewState previousState, PartialMainState changedStatePart) {
        MainViewState newState = previousState;

        if (changedStatePart instanceof PartialMainState.Loading) {
            newState.loading = true;
        }

        if (changedStatePart instanceof PartialMainState.GotQuestion) {
            newState.loading = false;
            newState.questionShown = true;
            newState.answerShown = false;
            newState.textToShow = ((PartialMainState.GotQuestion) changedStatePart).question;
        }

        if (changedStatePart instanceof PartialMainState.GotAnswer) {
            newState.loading = false;
            newState.questionShown = false;
            newState.answerShown = true;
            newState.textToShow = ((PartialMainState.GotAnswer) changedStatePart).answer;
        }

        if (changedStatePart instanceof PartialMainState.Error) {
            newState.loading = false;
            newState.error = ((PartialMainState.Error) changedStatePart).error;
        }

        return newState;
    }
}
