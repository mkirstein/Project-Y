package com.teamlimo.project_y.quizResult;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.IViewManager;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.core.ViewManager;
import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.Calendar;

/**
 * Created by Marc on 07.04.2016.
 */
public class QuizResultActivity extends AppCompatActivity implements IQuizResultView {

    private QuizResultPresenter presenter;
    private QuizResultActivityView quizResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizResultView = new QuizResultActivityView(this);
        setContentView(quizResultView);
        setTitle(R.string.quizResult_title);
        presenter = PresenterFactory.getInstance().getPresenter(QuizResultPresenter.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);

        final Button submitHighscoreButton = (Button) findViewById(R.id.submitHighscoreButton);
        EditText playerNameEditText = (EditText) findViewById(R.id.playerNameEditText);

        submitHighscoreButton.setEnabled(playerNameEditText.getText().toString().trim().length() != 0);

        playerNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() != 0)
                    submitHighscoreButton.setEnabled(true);
                else
                    submitHighscoreButton.setEnabled(false);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            IViewManager vM = ViewManager.getInstance();
            vM.switchView(this, vM.getViewFactory().createMenuView());
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onSubmitHighscoreButtonClicked(View v) {
        EditText playerNameEditText = (EditText) findViewById(R.id.playerNameEditText);
        String playerName = playerNameEditText.getText().toString().trim();

        presenter.submitHighscore(playerName);
    }

    public void showResult(QuizResult quizResult) {
        TextView scoreTextView = (TextView) findViewById(R.id.quizResultScore);
        scoreTextView.setText(String.valueOf(quizResult.getFinalScore()));
        int correctAnswers = quizResult.getCorrectAnswers();
        int totalQuestions = quizResult.getIncorrectAnswers() + correctAnswers;

        quizResultView.setResultsForArc(totalQuestions, correctAnswers);
    }
}