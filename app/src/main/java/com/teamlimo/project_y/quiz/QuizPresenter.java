package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.entities.Question;

import java.util.ArrayList;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class QuizPresenter implements IQuizReceiver {

    public void buildNewQuiz() {
        new BuildQuizCommand(this).execute();
    }

    @Override
    public void receiveQuiz(ArrayList<Question> questions) {
        // ToDo: tell View to display first question
    }
}
