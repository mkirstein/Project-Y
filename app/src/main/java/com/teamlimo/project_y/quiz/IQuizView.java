package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.entities.Question;

/**
 * Created by Marc on 03.04.2016.
 */
public interface IQuizView {
    void displayQuestion(final Question question);

    void showNextQuestionButton();
    void showGoToQuizResultButton();

    void updateProgress(int progress);
    void updateScore(long score);

    void highlightAnswers();
}
