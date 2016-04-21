package com.teamlimo.project_y.highscore;

import com.teamlimo.project_y.core.DatabaseManager;
import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class HighscorePresenter implements ITopHighscoresReceiver, IRankFromScoreReceiver {

    private IHighscoreView view;
    private ArrayList<HighscoreEntry> highscoreEntries;
    private int highlightedHighscoreIndex = -1;

    public void setView(IHighscoreView view) {
        this.view = view;
        new GetTopHighscoresCommand(this).execute();
    }

    @Override
    public void receiveTopHighscores(List<HighscoreEntry> highscoreEntries) {

        this.highscoreEntries = new ArrayList<>(highscoreEntries);

        if (view != null)
            view.displayHighscore(highscoreEntries, highlightedHighscoreIndex);
    }

    public void highlightLastHighscoreEntry(long score, String playerName) {
        new GetRankFromScoreCommand(this, score).execute();
    }

    @Override
    public void receiveRankFromScore(int rank) {
        highlightedHighscoreIndex = rank - 1;
    }

    public void resetHighlighting() {
        highlightedHighscoreIndex = -1;
    }
}
