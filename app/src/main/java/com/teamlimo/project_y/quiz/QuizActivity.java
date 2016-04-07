package com.teamlimo.project_y.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.IViewManager;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.core.ViewManager;
import com.teamlimo.project_y.entities.Answer;
import com.teamlimo.project_y.entities.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends Activity implements IQuizView {

    private QuizPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        presenter = PresenterFactory.getInstance().getPresenter(QuizPresenter.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
    }

    public void showNextQuestionButton() {
        setNextQuestionButtonVisibility(true);
    }

    public void nextQuestion(View v) {
        setNextQuestionButtonVisibility(false);
        presenter.showNextQuestion();
    }

    public void showResultsButton() {
        setResultButtonVisibility(true);
    }

    public void gotoResults(View v) {
        IViewManager vM = ViewManager.getInstance();
        vM.switchView(this, vM.getViewFactory().createQuizResultView());
        presenter.reset();
    }

    private void setNextQuestionButtonVisibility(boolean visible) {
        Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        int visibility = (visible) ? View.VISIBLE : View.GONE;
        nextQuestionButton.setVisibility(visibility);
    }

    private void setResultButtonVisibility(boolean visible) {
        Button resultButton = (Button) findViewById(R.id.gotoResultsButton);
        int visibility = (visible) ? View.VISIBLE : View.GONE;
        resultButton.setVisibility(visibility);
    }

    public void displayQuestion(final Question question) {

        final ListView listView = (ListView) findViewById(R.id.answerList);


        runOnUiThread(new Runnable() {
            public void run() {


                TextView categoryView = (TextView) findViewById(R.id.category_name);
                TextView questionView = (TextView) findViewById(R.id.question_name);

                categoryView.setText(question.getCategory());
                questionView.setText(question.getQuestionText());

                ArrayList<Answer> answers = question.getAnswers();
                ArrayList<Map<String, String>> transformedAnswers = new ArrayList<Map<String, String>>();

                for (Answer answer : answers) {
                    Map<String, String> answersMap = new HashMap<String, String>();
                    answersMap.put("id", String.valueOf(answer.getId()));
                    answersMap.put("text", answer.getText());
                    answersMap.put("isCorrect", String.valueOf(answer.isCorrect()));
                    transformedAnswers.add(answersMap);
                }

                ListAdapter adapter = new SimpleAdapter(
                        QuizActivity.this,
                        transformedAnswers,
                        R.layout.answerlist_item,
                        new String[]{"text", "id"},
                        new int[]{R.id.answerText, R.id.answerID});

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(!presenter.canSelectAnswer()) {
                            return;
                        }

                        TextView answerIDView = (TextView) view.findViewById(R.id.answerID);
                        long answerId = Long.parseLong((String)answerIDView.getText());
                        boolean isCorrectAnswer = presenter.processSelectedAnswer(answerId);
                        if(isCorrectAnswer) {
                            view.setBackgroundColor(0xFF00FF00);
                        } else {
                            view.setBackgroundColor(0xFFFF0000);
                        }
                    }
                });
            }
        });
    }


    public void displayError(String title, String message) {
        final Activity sourceView = this;
        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(this);
        errorDialogBuilder.setCancelable(false);
        errorDialogBuilder.setTitle(title);
        errorDialogBuilder.setMessage(message);
        errorDialogBuilder.setPositiveButton("Erneut versuchen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IViewManager vM = ViewManager.getInstance();
                vM.switchView(sourceView, vM.getViewFactory().createQuizView());
            }
        });
        errorDialogBuilder.setNeutralButton("Zurück", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IViewManager vM = ViewManager.getInstance();
                vM.switchView(sourceView, vM.getViewFactory().createMenuView());
            }
        });
        errorDialogBuilder.create().show();
    }


}
