package com.teamlimo.project_y.quizResult;

/**
 * Created by Project0rion on 08.04.2016.
 */
public class QuizResult {
    private long finalScore;
    private int correctAnswers;
    private int incorrectAnswers;

    public void setFinalScore(long value) {
        finalScore = value;
    }

    public void setCorrectAnswers(int value) {
        correctAnswers = value;
    }

    public void setIncorrectAnswers(int value) {
        incorrectAnswers = value;
    }

    public long getFinalScore() {
        return finalScore;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
