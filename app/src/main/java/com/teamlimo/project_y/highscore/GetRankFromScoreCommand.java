package com.teamlimo.project_y.highscore;

import android.os.AsyncTask;

import com.teamlimo.project_y.core.DatabaseManager;
import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Project0rion on 07.04.2016.
 */
public class GetRankFromScoreCommand extends AsyncTask<String, String, Integer> {

    private IRankFromScoreReceiver resultReceiver;
    private long score;

    public GetRankFromScoreCommand(IRankFromScoreReceiver resultReceiver, long score) {
        this.resultReceiver = resultReceiver;
        this.score = score;
    }

    @Override
    protected Integer doInBackground(String... params) {
        HighscoreEntry dummyEntry = new HighscoreEntry(score, "", Calendar.getInstance().getTime());
        int rank = DatabaseManager.getInstance().queryPrimitive(Integer.class, "get_rank_from_score", dummyEntry);

        return rank;
    }

    @Override
    protected void onPostExecute(Integer rank) {
        super.onPostExecute(rank);

        resultReceiver.receiveRankFromScore(rank);
    }
}
