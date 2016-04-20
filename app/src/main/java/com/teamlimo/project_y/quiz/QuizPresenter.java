package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.core.AppSettings;
import com.teamlimo.project_y.core.ErrorCodes;
import com.teamlimo.project_y.core.IViewManager;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.core.ViewManager;
import com.teamlimo.project_y.entities.Answer;
import com.teamlimo.project_y.entities.Question;
import com.teamlimo.project_y.quizResult.QuizResult;
import com.teamlimo.project_y.quizResult.QuizResultPresenter;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class QuizPresenter implements IQuizReceiver {

    private IQuizView view;
    private QuizCountDownTimer quizTimer;
    private QuizScoreCalculator scoreCalculator;
    private ArrayList<Question> questions;
    private int currentQuestionIndex;
    private boolean answerSelectable;
    private Answer selectedAnswer;

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
                    enableNextViewIfAllowed();
                }
            };
        }

        if(questions == null || questions.isEmpty()) {
            new BuildQuizCommand(this).execute();
            currentQuestionIndex = 0;
            answerSelectable = true;
        } else {
            view.displayQuestion(questions.get(currentQuestionIndex));
            quizTimer.start();
        }

    }

    public void showNextQuestion() {
        currentQuestionIndex++;
        quizTimer.reset();
        selectedAnswer = null;

        if(currentQuestionIndex >= questions.size()) {
            view.showGoToQuizResultButton();
        } else {
            answerSelectable = true;
            view.displayQuestion(questions.get(currentQuestionIndex));
            quizTimer.start();
        }
    }

    public boolean canSelectAnswer() {
        return answerSelectable;
    }

    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    public Question getCurrentQuestion() {
        if(questions != null) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public void enableNextViewIfAllowed() {
        if(quizTimer.isTimerFinished()) {
            if (questions != null && currentQuestionIndex + 1 >= questions.size()) {
                view.showGoToQuizResultButton();
            } else {
                view.showNextQuestionButton();
            }
            view.highlightAnswers();

        }
    }

    public boolean onAnswerSelected(long answerId) {

        answerSelectable = false;
        Question currentQuestion = questions.get(currentQuestionIndex);
        selectedAnswer = getAnswerWithId(answerId, currentQuestion);


        quizTimer.stop();
        scoreCalculator.processQuestionFinished(currentQuestion, selectedAnswer, quizTimer.getRelativeElapsedTime());
        view.updateScore(scoreCalculator.getScore());
        enableNextViewIfAllowed();

        return selectedAnswer.isCorrect();
    }

    public boolean isAnswerCorrect(Question contextQuestion, long answerId) {
        Answer answer = getAnswerWithId(answerId, contextQuestion);
        return answer.isCorrect();
    }

    private Answer getAnswerWithId(long id, Question containingQuestion) {
        for(Answer answer : containingQuestion.getAnswers()) {
            if(answer.getId() == id) {
                return answer;
            }
        }

        return null;
    }

    public void goToQuizResult() {
        // set Data of QuizResult
        QuizResult quizResult = new QuizResult();
        quizResult.setFinalScore(scoreCalculator.getScore());
        quizResult.setCorrectAnswers(scoreCalculator.getCorrectAnswers());
        quizResult.setIncorrectAnswers(scoreCalculator.getIncorrectAnswers());

        QuizResultPresenter quizResultPresenter = PresenterFactory.getInstance().getPresenter(QuizResultPresenter.class);
        quizResultPresenter.init(quizResult);

        IViewManager vM = ViewManager.getInstance();
        vM.switchView(view, vM.getViewFactory().createQuizResultView());
    }

    @Override
    public void receiveQuiz(ArrayList<Question> questions) {
        final IViewManager vM = ViewManager.getInstance();

        // Connection error handling
        if(questions == null) {
            vM.showDialog(view, ErrorCodes.CONNECTION_FAILED, false, createRetryConnectingCallable(), createGoToMenuCallable());
        } // Database error, no results
        else if(questions.isEmpty()) {
            vM.showDialog(view, ErrorCodes.NO_DATA_FOUND, false, createRetryConnectingCallable(), createGoToMenuCallable());
        } else {
            this.questions = questions;
            scoreCalculator = new QuizScoreCalculator();
            view.updateScore(scoreCalculator.getScore());
            quizTimer.reset();
            showQuiz();
        }
    }

    private Callable createRetryConnectingCallable() {
        return new Callable() {
            @Override
            public Object call() throws Exception {
                // retry connecting
                final IViewManager vM = ViewManager.getInstance();
                vM.switchView(view, vM.getViewFactory().createQuizView());
                return null;
            }
        };
    }

    private Callable createGoToMenuCallable() {
        return new Callable() {
            @Override
            public Object call() throws Exception {
                // retry connecting
                final IViewManager vM = ViewManager.getInstance();
                vM.switchView(view, vM.getViewFactory().createMenuView());
                return null;
            }
        };
    }

    public boolean hasActiveQuiz() {
        return questions != null && !questions.isEmpty();
    }

    public void reset() {
        quizTimer.reset();
        answerSelectable = true;
        questions = null;
        currentQuestionIndex = 0;
        quizTimer = null;
        scoreCalculator = null;
        selectedAnswer = null;
        view.updateScore(0);
    }
}
