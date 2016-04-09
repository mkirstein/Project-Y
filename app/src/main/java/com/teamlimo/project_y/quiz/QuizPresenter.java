package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.core.AppSettings;
import com.teamlimo.project_y.core.IViewManager;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.core.ViewManager;
import com.teamlimo.project_y.entities.Answer;
import com.teamlimo.project_y.entities.Question;
import com.teamlimo.project_y.quizResult.QuizResult;
import com.teamlimo.project_y.quizResult.QuizResultPresenter;

import java.util.ArrayList;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class QuizPresenter implements IQuizReceiver {

    private IQuizView view;
    private QuizCountDownTimer quizTimer;
    private ArrayList<Question> questions;
    private int currentQuestionIndex;
    private boolean answerSelectable;

    public void setView(IQuizView view) {
        this.view = view;
        showQuiz();
    }

    private void showQuiz() {

        if(quizTimer == null) {
            final long timerLength = AppSettings.QUIZTIMER_LENGTH;
            final long timerInterval = AppSettings.QUIZTIMER_INTERVAL;
            quizTimer = new QuizCountDownTimer(timerLength, timerInterval) {

                @Override
                void onTimerUpdate(long delta) {
                    int inverseProgress = (int)((1 - ((float)delta / (float)timerLength)) * 100);
                    view.updateProgress(inverseProgress);
                }

                @Override
                void onTimerFinished() {
                    answerSelectable = false;
                    showButtons();
                }
            };
        }

        if(questions == null || questions.isEmpty()) {
            new BuildQuizCommand(this).execute();
            currentQuestionIndex = 0;
            answerSelectable = true;
        } else {
            view.displayQuestion(questions.get(currentQuestionIndex));
            startTimer();
        }

    }

    public void showNextQuestion() {
        currentQuestionIndex++;
        quizTimer.reset();

        if(currentQuestionIndex >= questions.size()) {
            view.showGoToQuizResultButton();
        } else {
            answerSelectable = true;
            view.displayQuestion(questions.get(currentQuestionIndex));
            startTimer();
        }
    }

    public boolean canSelectAnswer() {
        return answerSelectable;
    }

    public void startTimer() {
        quizTimer.start();
    }

    public void stopTimer() {
        quizTimer.stop();
    }

    public void showButtons() {
        if(quizTimer.isTimerFinished()) {
            if (questions != null && currentQuestionIndex + 1 >= questions.size()) {
                view.showGoToQuizResultButton();
            } else {
                view.showNextQuestionButton();
            }
        }
    }

    public boolean processSelectedAnswer(long answerId) {

        answerSelectable = false;
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
        stopTimer();
        showButtons();
        return isCorrect;
    }

    public void goToQuizResult() {
        // set Data of QuizResult
        QuizResult quizResult = new QuizResult();
        quizResult.setFinalScore(150);
        quizResult.setCorrectAnswers(4);
        quizResult.setIncorrectAnswers(1);

        QuizResultPresenter quizResultPresenter = PresenterFactory.getInstance().getPresenter(QuizResultPresenter.class);
        quizResultPresenter.init(quizResult);

        IViewManager vM = ViewManager.getInstance();
        vM.switchView(view, vM.getViewFactory().createQuizResultView());
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
            quizTimer.reset();
            showQuiz();
        }
    }

    public void reset() {
        answerSelectable = true;
        questions = null;
        currentQuestionIndex = 0;
        quizTimer.reset();
    }
}
