package com.teamlimo.project_y.quizResult;

import android.os.AsyncTask;

import com.teamlimo.project_y.core.DatabaseManager;
import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.Calendar;

/**
 * Created by Project0rion on 14.04.2016.
 */
public class SubmitHighscoreCommand extends AsyncTask<String, String, String> {

    long score;
    String playerName;

    public SubmitHighscoreCommand(long score, String playerName) {
        this.score = score;
        this.playerName = playerName;
    }

    @Override
    protected String doInBackground(String... params) {
        HighscoreEntry newHighscoreEntry = new HighscoreEntry(score, playerName, Calendar.getInstance().getTime());
        DatabaseManager.getInstance().insertOrUpdate("add_highscore_operation", newHighscoreEntry);

        return null;
    }
}
