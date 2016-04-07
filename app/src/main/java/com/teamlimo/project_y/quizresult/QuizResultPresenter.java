package com.teamlimo.project_y.quizresult;

/**
 * Created by Marc on 07.04.2016.
 */
public class QuizResultPresenter {

    private IQuizResultView view;

    public void setView(IQuizResultView view) {
        this.view = view;
        showResults();
    }

    private void showResults() {

    }
}
