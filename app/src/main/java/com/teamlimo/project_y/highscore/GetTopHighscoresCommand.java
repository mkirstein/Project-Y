package com.teamlimo.project_y.highscore;

import android.os.AsyncTask;

import com.teamlimo.project_y.core.DatabaseManager;
import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.ArrayList;

/**
 * Created by Project0rion on 07.04.2016.
 */
public class GetTopHighscoresCommand extends AsyncTask<String, String, ArrayList<HighscoreEntry>> {

    private ITopHighscoresReceiver resultReceiver;

    public GetTopHighscoresCommand(ITopHighscoresReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }

    @Override
    protected ArrayList<HighscoreEntry> doInBackground(String... params) {
        ArrayList<HighscoreEntry> topHighscores = DatabaseManager.getInstance().queryMany(HighscoreEntry.class, "query_top_highscores_operation");

        return topHighscores;
    }

    @Override
    protected void onPostExecute(ArrayList<HighscoreEntry> highscoreEntries) {
        super.onPostExecute(highscoreEntries);

        resultReceiver.receiveTopHighscores(highscoreEntries);
    }
}
