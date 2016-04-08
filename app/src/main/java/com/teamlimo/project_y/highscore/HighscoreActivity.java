package com.teamlimo.project_y.highscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.entities.HighscoreEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

                    // reformat date
                    DateFormat df = new SimpleDateFormat("(dd.MM.yyyy)");
                    highscoreEntryMap.put("date", df.format(highscoreEntry.getDate()));

                    transformedHighscoreEntries.add(highscoreEntryMap);
                }

                Collections.sort(transformedHighscoreEntries, new Comparator<Map<String, String>>() {
                    @Override
                    public int compare(Map<String, String> lhs, Map<String, String> rhs) {
                        long firstScore = Long.parseLong(lhs.get("score"));
                        long secondScore = Long.parseLong(rhs.get("score"));

                        if (firstScore == secondScore)
                            return 0;

                        return firstScore < secondScore ? 1 : -1;
                    }
                });

                ListAdapter adapter = new SimpleAdapter(
                        HighscoreActivity.this,
                        transformedHighscoreEntries,
                        R.layout.highscorelist_item,
                        new String[] { "date", "score", "player_name"},
                        new int[] { R.id.highscoreEntry_date, R.id.highscoreEntry_score, R.id.highscoreEntry_playerName });

                listView.setAdapter(adapter);
            }
        });
    }
}
