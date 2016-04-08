package com.teamlimo.project_y.highscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.entities.HighscoreEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighscoreActivity extends AppCompatActivity implements IHighscoreView {

    private HighscorePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        setTitle(R.string.highscore_title);
        presenter = PresenterFactory.getInstance().getPresenter(HighscorePresenter.class);
        presenter.setView(this);
    }

    @Override
    public void displayHighscore(final List<HighscoreEntry> highscoreEntries) {
        final ListView listView = (ListView) findViewById(R.id.highscoreList);

        runOnUiThread(new Runnable() {
            public void run() {

                ArrayList<Map<String, String>> transformedHighscoreEntries = new ArrayList<>();

                for (HighscoreEntry highscoreEntry : highscoreEntries) {
                    Map<String, String> highscoreEntryMap = highscoreEntry.saveToMap();
                    transformedHighscoreEntries.add(highscoreEntryMap);
                }

                ListAdapter adapter = new SimpleAdapter(
                        HighscoreActivity.this,
                        transformedHighscoreEntries,
                        R.layout.highscorelist_item,
                        new String[] { "score", "player_name"},
                        new int[] { R.id.score, R.id.playerName });

                listView.setAdapter(adapter);
            }
        });
    }
}
