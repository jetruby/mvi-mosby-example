package com.example.admi.mvimosby;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvi.MviActivity;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Random;

import io.reactivex.Observable;

public class MainActivity extends MviActivity<MainView, MainPresenter> implements MainView {

    TextView textView;
    Button askQuestionButton;
    Button getAnswerButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        askQuestionButton = findViewById(R.id.btn_question);
        getAnswerButton = findViewById(R.id.btn_answer);
        progressBar = findViewById(R.id.progress_bar);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public Observable<Integer> askQuestionIntent() {
        return RxView.clicks(askQuestionButton)
                .map(click -> new Random().nextInt());
    }

    @Override
    public Observable<Integer> getAnswerIntent() {
        return RxView.clicks(getAnswerButton)
                .map(click -> new Random().nextInt());
    }

    @Override
    public void render(MainViewState viewState) {

        if (viewState.loading) {
            progressBar.setVisibility(View.VISIBLE);
            askQuestionButton.setEnabled(false);
            getAnswerButton.setEnabled(false);
        } else if (viewState.error != null) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else if (!viewState.questionShown || viewState.answerShown) {
            progressBar.setVisibility(View.GONE);
            askQuestionButton.setEnabled(true);
            getAnswerButton.setEnabled(false);
            textView.setText(viewState.textToShow);
        } else {
            progressBar.setVisibility(View.GONE);
            askQuestionButton.setEnabled(false);
            getAnswerButton.setEnabled(true);
            textView.setText(viewState.textToShow);
        }
    }
}