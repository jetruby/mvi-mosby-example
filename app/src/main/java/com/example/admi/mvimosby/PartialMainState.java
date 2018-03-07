package com.example.admi.mvimosby;

public interface PartialMainState {

    final class Loading implements PartialMainState {
    }

    final class GotQuestion implements PartialMainState {
        public String question;

        public GotQuestion(String question) {
            this.question = question;
        }
    }

    final class GotAnswer implements PartialMainState {
        public String answer;

        public GotAnswer(String answer) {
            this.answer = answer;
        }
    }

    final class Error implements PartialMainState {
        public Throwable error;

        public Error(Throwable error) {
            this.error = error;
        }
    }


}
