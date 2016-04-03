package com.teamlimo.project_y.entities;

import com.teamlimo.project_y.core.IEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Project0rion on 28.03.2016.
 */
public class Question implements IEntity {

    private long id;
    private String text;
    private ArrayList<Answer> answers;
    private String category;

    @Override
    public void createFromJSON(JSONObject jobj) {

        try {
            id = jobj.getLong("id");
            text = jobj.getString("text");
            category = jobj.getString("categoryText");

            JSONArray jsonAnswers = jobj.getJSONArray("answers");

            answers = new ArrayList<Answer>();
            for (int i = 0; i < jsonAnswers.length(); i++) {
                JSONObject jsonAnswer = jsonAnswers.getJSONObject(i);
                Answer answer = new Answer();
                answer.createFromJSON(jsonAnswer);
                answers.add(answer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }
}
