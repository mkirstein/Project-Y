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

    public void setView(IQuizView view) {
        this.view = view;
        showQuiz();
    }

    private void showQuiz() {

        if(questions == null || questions.isEmpty()) {
            new BuildQuizCommand(this).execute();
            currentQuestionIndex = 0;
        }

        if(questions != null) {
            view.displayQuestion(questions.get(currentQuestionIndex));
        }

    }

    public void showNextQuestion() {
        currentQuestionIndex++;

        if(currentQuestionIndex >= questions.size()) {
            view.showGoToQuizResultButton();
        } else {
            view.displayQuestion(questions.get(currentQuestionIndex));
            answerSelected = false;
        }
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
            view.showGoToQuizResultButton();
        } else {
            view.showNextQuestionButton();
        }
        return isCorrect;
    }

    @Override
    public void receiveQuiz(ArrayList<Question> questions) {
        // Connection error handling
        if(questions == null) {
            view.displayConnectionFailedError();
        } // Database error, no results
        else if(questions.isEmpty()) {
            view.displayNoDataFoundError();
        } else {
            this.questions = questions;
            showQuiz();
        }
    }

    public void reset() {
        answerSelected = false;
        questions = null;
        currentQuestionIndex = 0;
    }
}
