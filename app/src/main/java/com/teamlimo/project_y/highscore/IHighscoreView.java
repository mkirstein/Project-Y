package com.teamlimo.project_y.highscore;

import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.List;

/**
 * Created by Project0rion on 07.04.2016.
 */
public interface IHighscoreView {

    void displayHighscore(List<HighscoreEntry> highscoreEntries);
}
