package com.teamlimo.project_y.quizResult;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.biowink.clue.ArcUtils;
import com.teamlimo.project_y.R;

/**
 * Created by Marc on 09.04.2016.
 */
public class QuizResultActivityView extends LinearLayout {
    private boolean drawQuestionsArc = false;
    private float totalQuestions, correctAnswers;

    public QuizResultActivityView(Context context)
    {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_quiz_result, this);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(drawQuestionsArc) {
            drawQuestionsArc(canvas);
        }
        super.onDraw(canvas);
    }

    private void drawQuestionsArc(Canvas canvas) {
        float viewWidth = getWidth();
        float viewHeight = getHeight();

        float circleRadius = 450;
        float strokeWidth = 30;

        PointF centerPoint = new PointF(viewWidth / 2, viewHeight / 2);

        Paint greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(strokeWidth);

        Paint redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(strokeWidth);

        int colorCorrect = getResources().getColor(R.color.colorCorrect);
        int colorIncorrect = getResources().getColor(R.color.colorIncorrect);
        greenPaint.setColor(colorCorrect);
        redPaint.setColor(colorIncorrect);

        float angleGreenArc = (correctAnswers / totalQuestions) * 360;
        float angleRedArc = (360 - angleGreenArc) * -1;

        ArcUtils.drawArc(canvas, centerPoint, circleRadius, -90, angleGreenArc, greenPaint);
        ArcUtils.drawArc(canvas, centerPoint, circleRadius, -90, angleRedArc, redPaint);
    }

    public void setResultsForArc(int totalQuestions, int correctAnswers) {
        drawQuestionsArc = true;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        // Redraw view
        invalidate();
    }
}
