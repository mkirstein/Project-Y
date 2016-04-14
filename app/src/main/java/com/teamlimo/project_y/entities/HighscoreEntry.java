package com.teamlimo.project_y.entities;

import com.teamlimo.project_y.core.IEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Project0rion on 07.04.2016.
 */
public class HighscoreEntry implements IEntity {

    private long id;
    private long score;
    private String playerName;
    private Date date;

    public HighscoreEntry() {

    }

    public HighscoreEntry(long score, String playerName, Date date) {
        this.score = score;
        this.playerName = playerName;
        this.date = date;
    }

    @Override
    public void createFromJSON(JSONObject jobj) {

        try {
            id = jobj.getLong("id");
            score = jobj.getLong("score");
            playerName = jobj.getString("player_name");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date = format.parse(jobj.getString("date"));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> saveToMap() {
        Map<String, String> result = new HashMap<>();

        result.put("id", String.valueOf(id));
        result.put("score", String.valueOf(score));
        result.put("player_name", String.valueOf(playerName));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        result.put("date", df.format(date));

        return result;
    }

    public long getId() {
        return id;
    }

    public long getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Date getDate() {
        return date;
    }
}
