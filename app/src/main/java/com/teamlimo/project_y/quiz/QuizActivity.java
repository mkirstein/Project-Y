package com.teamlimo.project_y.quiz;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.entities.Answer;
import com.teamlimo.project_y.entities.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends Activity {

    private QuizPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        presenter = new QuizPresenter();

        presenter.buildNewQuiz();
    }

    public void displayQuestion(final Question question) {

        final ListView listView = (ListView) findViewById(R.id.answerList);

        runOnUiThread(new Runnable() {
            public void run() {

                ArrayList<Answer> answers = question.getAnswers();
                ArrayList<Map<String, String>> transformedAnswers = new ArrayList<Map<String, String>>();

                for (Answer answer : answers) {
                    Map<String, String> answersMap = new HashMap<String, String>();
                    answersMap.put("id", String.valueOf(answer.getId()));
                    answersMap.put("text", answer.getText());
                    answersMap.put("isCorrect", String.valueOf(answer.isCorrect()));
                    transformedAnswers.add(answersMap);
                }

                //ToDo eigenen Adapter schreiben, der die Buttons mit Daten befüllt
//                ListAdapter adapter = new SimpleAdapter(
//                        QuizActivity.this,
//                        transformedAnswers,
//                        R.layout.answerlist_item,
//                        new String[] { "text"},
//                        new int[] { R.id.anwser_button });
//
//                listView.setAdapter(adapter);
            }
        });
    }
}
