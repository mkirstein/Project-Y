package com.teamlimo.project_y.quizResult;

import com.teamlimo.project_y.core.UserData;
import com.teamlimo.project_y.core.UserDataManager;
import com.teamlimo.project_y.core.ViewManager;

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

        String lastUsedUserName = UserDataManager.getInstance().load().getUserName();

        view.showResult(quizResult, lastUsedUserName);
    }

    public void submitHighscore(String playerName) {
        if (playerName.trim().length() == 0)
            return;

        new SubmitHighscoreCommand(quizResult.getFinalScore(), playerName).execute();

        UserData userData = UserDataManager.getInstance().load();
        userData.setUserName(playerName);
        UserDataManager.getInstance().save(userData);

        ViewManager.getInstance().switchView(view, ViewManager.getInstance().getViewFactory().createHighscoreView());
    }
}
