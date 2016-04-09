package com.teamlimo.project_y.quizResult;

/**
 * Created by Marc on 07.04.2016.
 */
public class QuizResultPresenter {

    private IQuizResultView view;
    private QuizResult quizResult;

    public void init(QuizResult quizResult) {
        this.quizResult = quizResult;
    }

    public void setView(IQuizResultView view) {
        this.view = view;
        showResults();
    }

    private void showResults() {
        if (quizResult == null)
            return;

        view.showResult(quizResult);
    }
}
