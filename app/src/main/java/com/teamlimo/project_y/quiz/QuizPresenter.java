package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.entities.Question;

import java.util.ArrayList;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class QuizPresenter implements IQuizReceiver {

    private IQuizView view;

    public void setView(IQuizView view) {
        this.view = view;
    }

    public void initQuiz() {

    }

    public void buildNewQuiz() {
        new BuildQuizCommand(this).execute();
    }

    @Override
    public void receiveQuiz(ArrayList<Question> questions) {
        view.displayQuestion(questions.get(0));
    }
}
