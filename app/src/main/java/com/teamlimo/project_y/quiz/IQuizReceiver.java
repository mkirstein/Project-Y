package com.teamlimo.project_y.quiz;

import com.teamlimo.project_y.entities.Question;

import java.util.ArrayList;

/**
 * Created by Project0rion on 02.04.2016.
 */
public interface IQuizReceiver {

    void receiveQuiz(ArrayList<Question> questions);
}
