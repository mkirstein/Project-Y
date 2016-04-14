package com.teamlimo.project_y.quizResult;

import com.teamlimo.project_y.core.DatabaseManager;
import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.Calendar;

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

    public void submitHighscore(String playerName) {
        if (playerName.trim().length() == 0)
            return;

        new SubmitHighscoreCommand(quizResult.getFinalScore(), playerName).execute();
    }
}
