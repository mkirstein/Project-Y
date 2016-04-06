package com.teamlimo.project_y.entities;

import com.teamlimo.project_y.core.IEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Project0rion on 28.03.2016.
 */
public class Answer implements IEntity {

    private long id;
    private String text;
    private boolean isCorrect;

    @Override
    public void createFromJSON(JSONObject jobj) {

        try {
            id = jobj.getLong("id");
            text = jobj.getString("text");
            isCorrect = jobj.getBoolean("isCorrect");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> saveToMap() {
        Map<String, String> result = new HashMap<>();

        result.put("id", String.valueOf(id));
        result.put("text", String.valueOf(text));
        result.put("isCorrect", String.valueOf(isCorrect));

        return result;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
