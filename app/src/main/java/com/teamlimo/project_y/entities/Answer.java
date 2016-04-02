package com.teamlimo.project_y.entities;

import com.teamlimo.project_y.core.IEntity;

import org.json.JSONException;
import org.json.JSONObject;

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
}
