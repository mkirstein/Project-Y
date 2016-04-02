package com.teamlimo.project_y.quiz;

import android.content.Intent;
import android.os.AsyncTask;

import com.teamlimo.project_y.core.DatabaseManager;
import com.teamlimo.project_y.core.ViewManager;
import com.teamlimo.project_y.entities.Question;

import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Project0rion on 02.04.2016.
 */
public class BuildQuizCommand extends AsyncTask<String, String, ArrayList<Question>> {

    IQuizReceiver resultReceiver;

    public BuildQuizCommand(IQuizReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... args) {

        ArrayList<Question> questions = DatabaseManager.getInstance().queryMany(Question.class, "build_quiz_operation");

        return questions;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);

        resultReceiver.receiveQuiz(questions);
    }
}
