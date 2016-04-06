package com.teamlimo.project_y.quiz;

import android.app.AlertDialog;

import com.teamlimo.project_y.core.ViewManager;
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
        // Connection error handling
        if(questions == null) {
            view.displayError("Keine Verbindung möglich", "Es konnte keine Verbindung mit dem Server aufgebaut werden!");
        } // Database error, no results
        else if(questions.isEmpty()) {
            view.displayError("Keine Daten vorhanden", "Es konnte keine Daten geladen werden!");
        } else {
            view.displayQuestion(questions.get(0));
        }
    }
}
