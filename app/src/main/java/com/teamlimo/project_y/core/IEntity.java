package com.teamlimo.project_y.core;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Project0rion on 02.04.2016.
 */
public interface IEntity {

    void createFromJSON(JSONObject jobj);
    Map<String, String> saveToMap();
}
