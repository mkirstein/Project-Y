package com.teamlimo.project_y.quiz;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Marc on 08.04.2016.
 */
public abstract class QuizCountDownTimer {

    private Timer timer;
    private boolean timerStarted = false, timerFinished = false;
    private long timerLength, timerInterval;
    private double relativeElapsedTime;

    public QuizCountDownTimer(long timerLength, long timerInterval) {
        this.timerLength = timerLength;
        this.timerInterval = timerInterval;
        relativeElapsedTime = 0;
    }

    public void start() {
        if(timerStarted) {
            return;
        }

        final QuizCountDownTimer quizTimer = this;
        TimerTask task = new TimerTask() {
            long startTime = System.currentTimeMillis();
            @Override
            public void run() {
                long delta = System.currentTimeMillis() - startTime;
                if (delta > timerLength) {
                    stop();
                    relativeElapsedTime = 1;
                    onTimerFinished();
                } else {
                    relativeElapsedTime = (1 - ((double)delta / (double)timerLength));
                    onTimerUpdate(delta);
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, timerInterval);
        relativeElapsedTime = 0;
        timerStarted = true;
    }

    public void stop() {
        timer.cancel();
        timerFinished = true;
    }

    public boolean isTimerStarted() {
        return timerStarted;
    }

    public boolean isTimerFinished() {
        return timerFinished;
    }

    public void reset() {
        timerStarted = false;
        timerFinished = false;
    }

    abstract void onTimerUpdate(long delta);

    abstract void onTimerFinished();

    public double getRelativeElapsedTime() {
        return relativeElapsedTime;
    }
}
