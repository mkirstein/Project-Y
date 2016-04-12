package com.teamlimo.project_y.quiz;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.entities.Answer;
import com.teamlimo.project_y.entities.Question;

import java.util.ArrayList;
import java.util.List;
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
        presenter.enableNextViewIfAllowed();
        if(!presenter.canSelectAnswer()) {
            highlightAnswers();
        }
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

    @Override
    public void updateProgress(int progress) {
        ProgressBar leftBar = (ProgressBar) findViewById(R.id.quiz_progressbar_left);
        ProgressBar rightBar = (ProgressBar) findViewById(R.id.quiz_progressbar_right);
        leftBar.setProgress(progress);
        rightBar.setProgress(progress);
    }

    @Override
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
                        boolean isCorrectAnswer = presenter.onAnswerSelected(answerId);

                        if (isCorrectAnswer) {
                            view.setBackgroundResource(R.color.colorCorrect);
                        } else {
                            view.setBackgroundResource(R.color.colorIncorrect);

                            for (View answerView : getCorrectAnswerViews(question, parent)) {
                                startPulseAnimation(answerView);
                            }
                        }

                        TextView answerTextView = (TextView) view.findViewById(R.id.answerText);
                        answerTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightText));
                    }
                });
            }
        });
    }

    private List<View> getCorrectAnswerViews(Question contextQuestion, AdapterView parentView) {
        List<View> result = new ArrayList<>();

        for (int i = 0; i < parentView.getCount(); i++) {
            Object item = parentView.getChildAt(i);

            if (!(item instanceof View))
                continue;

            View itemAsView = (View) item;
            TextView answerIDView = (TextView) itemAsView.findViewById(R.id.answerID);
            long answerId = Long.parseLong((String) answerIDView.getText());

            if (presenter.isAnswerCorrect(contextQuestion, answerId))
                result.add(itemAsView);
        }

        return result;
    }

    private View getSelectedAnswerView(long answerId) {
        AdapterView parentView = (ListView) findViewById(R.id.answerList);

        for (int i = 0; i < parentView.getCount(); i++) {
            Object item = parentView.getChildAt(i);

            if (!(item instanceof View))
                continue;

            View itemAsView = (View) item;
            TextView answerIDView = (TextView) itemAsView.findViewById(R.id.answerID);
            long answerIdOfView = Long.parseLong((String) answerIDView.getText());

            if (answerIdOfView == answerId)
                return itemAsView;
        }
        return null;
    }

    private void highlightAnswers() {
        Answer selectedAnswer = presenter.getSelectedAnswer();
        Question currentQuestion = presenter.getCurrentQuestion();
        boolean selectedAnswerIsCorrect = false;

        if(selectedAnswer != null) {
            long selectedAnswerID = selectedAnswer.getId();
            View view = getSelectedAnswerView(selectedAnswerID);
            selectedAnswerIsCorrect = presenter.isAnswerCorrect(currentQuestion, selectedAnswerID);

            if(view != null) {
                if (selectedAnswerIsCorrect) {
                    view.setBackgroundResource(R.color.colorCorrect);
                } else {
                    view.setBackgroundResource(R.color.colorIncorrect);
                }
            }
        }

        // Show correct answer
        if(!selectedAnswerIsCorrect) {
            for (View answerView : getCorrectAnswerViews(currentQuestion, (ListView)findViewById(R.id.answerList))) {
                startPulseAnimation(answerView);
            }
        }
    }

    private void startPulseAnimation(final View view) {
        int colorFrom = getResources().getColor(R.color.colorCorrectTransparent);
        int colorTo = getResources().getColor(R.color.colorCorrect);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(500);
        colorAnimation.setRepeatCount(Animation.INFINITE);
        colorAnimation.setRepeatMode(Animation.REVERSE);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }
}
