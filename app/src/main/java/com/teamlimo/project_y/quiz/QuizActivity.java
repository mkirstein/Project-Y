package com.teamlimo.project_y.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamlimo.project_y.R;

public class QuizActivity extends AppCompatActivity {

    private QuizPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        presenter = new QuizPresenter();
    }
}
