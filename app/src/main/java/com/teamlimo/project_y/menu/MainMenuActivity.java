package com.teamlimo.project_y.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.teamlimo.project_y.R;

public class MainMenuActivity extends Activity {

    private MainMenuPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainMenuPresenter(this);
    }

    public void onButtonClicked(View v){

        switch (v.getId()) {

            case R.id.playQuizButton:
                presenter.startQuiz();
                break;

            case R.id.viewHighscoreButton:
                presenter.viewHighscore();
                break;
        }
    }
}
