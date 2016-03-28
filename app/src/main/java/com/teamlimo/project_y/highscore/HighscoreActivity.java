package com.teamlimo.project_y.highscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamlimo.project_y.R;

public class HighscoreActivity extends AppCompatActivity {

    private HighscorePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        presenter = new HighscorePresenter();
    }
}
