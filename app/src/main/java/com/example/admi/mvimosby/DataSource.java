package com.example.admi.mvimosby;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class DataSource {

    public Observable<String> askQuestion(int questionId) {
        return Observable.just("Do you like Ice Cream?")
                .delay(3000, TimeUnit.MILLISECONDS);
    }

    public Observable<String> getAnswer(int questionId) {
        return Observable.just("Yes, I do!")
                .delay(3000, TimeUnit.MILLISECONDS);
    }
}
