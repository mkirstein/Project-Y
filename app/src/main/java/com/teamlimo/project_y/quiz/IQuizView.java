package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.entities.Question;

/**
 * Created by Marc on 03.04.2016.
 */
public interface IQuizView {
    void displayQuestion(final Question question);
    void displayError(String title, String message);
    void showNextQuestionButton();
    void showResultsButton();
}
