package com.teamlimo.project_y.quizresult;

import android.app.Activity;
import android.os.Bundle;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.PresenterFactory;

/**
 * Created by Marc on 07.04.2016.
 */
public class QuizResultActivity extends Activity implements IQuizResultView {

    private QuizResultPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        presenter = PresenterFactory.getInstance().getPresenter(QuizResultPresenter.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
    }
}
