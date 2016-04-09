package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.core.AppSettings;
import com.teamlimo.project_y.entities.Answer;
import com.teamlimo.project_y.entities.Question;

/**
 * Created by Project0rion on 09.04.2016.
 */
public class QuizScoreCalculator {

    private long score;
    private int correctAnswers;
    private int incorrectAnswers;

    public void processQuestionFinished(Question question, Answer selectedAnswer, double relativeElapsedTime) {
        if (selectedAnswer.isCorrect())
            correctAnswers ++;
        else {
            incorrectAnswers++;
            return;
        }

        // score
        if (relativeElapsedTime > 0.5)
            score += AppSettings.MAX_SCORE_PER_QUESTION;
        else
            score += AppSettings.MAX_SCORE_PER_QUESTION * (relativeElapsedTime * 2);
    }

    public long getScore() {
        return score;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
