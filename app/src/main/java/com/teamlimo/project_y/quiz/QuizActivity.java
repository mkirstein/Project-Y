package com.teamlimo.project_y.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.IViewManager;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.core.ViewManager;
import com.teamlimo.project_y.entities.Answer;
import com.teamlimo.project_y.entities.Question;

import java.util.ArrayList;
import java.util.Map;

public class QuizActivity extends AppCompatActivity implements IQuizView {

    private QuizPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("");
        ProgressBar leftBar = (ProgressBar)(findViewById(R.id.quiz_progressbar_left));
        leftBar.setRotation(180);
        presenter = PresenterFactory.getInstance().getPresenter(QuizPresenter.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.showButtons();
    }

    public void showNextQuestionButton() {
        setNextQuestionButtonVisibility(true);
    }

    public void onNextQuestionButtonClicked(View v) {
        setNextQuestionButtonVisibility(false);
        presenter.showNextQuestion();
    }

    public void showGoToQuizResultButton() {
        setResultButtonVisibility(true);
    }

    public void onGoToQuizResultButtonClicked(View v) {
        presenter.goToQuizResult();
        setResultButtonVisibility(false);
        presenter.reset();
    }

    private void setNextQuestionButtonVisibility(boolean visible) {
        final Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        final int visibility = (visible) ? View.VISIBLE : View.GONE;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nextQuestionButton.setVisibility(visibility);
            }
        });
    }

    private void setResultButtonVisibility(boolean visible) {
        final Button resultButton = (Button) findViewById(R.id.gotoResultsButton);
        final int visibility = (visible) ? View.VISIBLE : View.GONE;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultButton.setVisibility(visibility);
            }
        });
    }

    public void updateProgress(int progress) {
        ProgressBar leftBar = (ProgressBar) findViewById(R.id.quiz_progressbar_left);
        ProgressBar rightBar = (ProgressBar) findViewById(R.id.quiz_progressbar_right);
        leftBar.setProgress(progress);
        rightBar.setProgress(progress);
    }

    public void displayQuestion(final Question question) {

        final ListView listView = (ListView) findViewById(R.id.answerList);

        runOnUiThread(new Runnable() {
            public void run() {

                TextView questionView = (TextView) findViewById(R.id.question_name);
                setTitle(question.getCategory());
                questionView.setText(question.getQuestionText());

                ArrayList<Answer> answers = question.getAnswers();
                ArrayList<Map<String, String>> transformedAnswers = new ArrayList<Map<String, String>>();

                for (Answer answer : answers) {
                    Map<String, String> answersMap = answer.saveToMap();
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
                        if (!presenter.canSelectAnswer()) {
                            return;
                        }

                        TextView answerIDView = (TextView) view.findViewById(R.id.answerID);
                        long answerId = Long.parseLong((String) answerIDView.getText());
                        boolean isCorrectAnswer = presenter.processSelectedAnswer(answerId);

                        if (isCorrectAnswer) {
                            view.setBackgroundResource(R.color.colorCorrect);
                        } else {
                            view.setBackgroundResource(R.color.colorIncorrect);
                        }

                        TextView answerTextView = (TextView) view.findViewById(R.id.answerText);
                        answerTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightText));
                    }
                });
            }
        });
    }

    public void displayConnectionFailedError() {
        displayError(getString(R.string.connection_failed_dialog_title), getString(R.string.connection_failed_dialog_message));
    }

    public void displayNoDataFoundError() {
        displayError(getString(R.string.no_data_found_dialog_title), getString(R.string.no_data_found_dialog_message));
    }

    private void displayError(String title, String message) {
        final Activity sourceView = this;
        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(this);
        errorDialogBuilder.setCancelable(false);
        errorDialogBuilder.setTitle(title);
        errorDialogBuilder.setMessage(message);
        errorDialogBuilder.setPositiveButton(getString(R.string.retry_dialog_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IViewManager vM = ViewManager.getInstance();
                vM.switchView(sourceView, vM.getViewFactory().createQuizView());
            }
        });
        errorDialogBuilder.setNeutralButton(getString(R.string.return_dialog_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IViewManager vM = ViewManager.getInstance();
                vM.switchView(sourceView, vM.getViewFactory().createMenuView());
            }
        });
        errorDialogBuilder.create().show();
    }


}
