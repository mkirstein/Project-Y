package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.entities.Answer;
import com.teamlimo.project_y.entities.Question;

import java.util.ArrayList;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class QuizPresenter implements IQuizReceiver {

    private IQuizView view;
    private ArrayList<Question> questions;
    private int currentQuestionIndex;
    private boolean answerSelected;
    private boolean quizFinished = false;

    public void setView(IQuizView view) {
        this.view = view;
        showQuiz();
    }

    private void showQuiz() {

        if(questions == null || questions.isEmpty() || quizFinished) {
            buildNewQuiz();
            currentQuestionIndex = 0;
            quizFinished = false;
        }

        if(questions != null) {
            view.displayQuestion(questions.get(currentQuestionIndex));
        }

    }

    public void showNextQuestion() {
        currentQuestionIndex++;
        if(currentQuestionIndex >= questions.size()) {
            view.showResultsButton();
        } else {
            view.displayQuestion(questions.get(currentQuestionIndex));
            answerSelected = false;
        }
    }

    private void buildNewQuiz() {
        new BuildQuizCommand(this).execute();
    }

    public boolean canSelectAnswer() {
        return !answerSelected;
    }

    public boolean processSelectedAnswer(long answerId) {

        answerSelected = true;
        boolean isCorrect = false;
        Question currentQuestion = questions.get(currentQuestionIndex);
        ArrayList<Answer> answers = currentQuestion.getAnswers();
        for(Answer answer : answers) {
            if(answer.getId() == answerId) {
                if(answer.isCorrect()) {
                    isCorrect = true;
                }
                break;
            }
        }
        if(currentQuestionIndex + 1 >= questions.size()) {
            view.showResultsButton();
        } else {
            view.showNextQuestionButton();
        }
        return isCorrect;
    }

    @Override
    public void receiveQuiz(ArrayList<Question> questions) {
        // Connection error handling
        if(questions == null) {
            view.displayError("Keine Verbindung möglich", "Es konnte keine Verbindung mit dem Server aufgebaut werden!");
        } // Database error, no results
        else if(questions.isEmpty()) {
            view.displayError("Keine Daten vorhanden", "Es konnte keine Daten geladen werden!");
        } else {
            this.questions = questions;
            showQuiz();
        }
    }

    public void reset() {
        answerSelected = false;
        quizFinished = true;
    }
}
