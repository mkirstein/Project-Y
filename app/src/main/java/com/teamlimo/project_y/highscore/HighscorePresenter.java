package com.teamlimo.project_y.highscore;

import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class HighscorePresenter implements ITopHighscoresReceiver {

    private IHighscoreView view;
    private ArrayList<HighscoreEntry> highscoreEntries;

    public HighscorePresenter() {
        new GetTopHighscoresCommand(this).execute();
    }

    public void setView(IHighscoreView view) {
        this.view = view;

        if (highscoreEntries != null && !highscoreEntries.isEmpty())
            view.displayHighscore(highscoreEntries);
    }

    @Override
    public void receiveTopHighscores(List<HighscoreEntry> highscoreEntries) {

        this.highscoreEntries = new ArrayList<>(highscoreEntries);

        if (view != null)
            view.displayHighscore(highscoreEntries);
    }
}
