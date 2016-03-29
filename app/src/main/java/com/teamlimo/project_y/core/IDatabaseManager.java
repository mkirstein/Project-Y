package com.teamlimo.project_y.core;

/**
 * Created by Project0rion on 27.03.2016.
 */
public interface IDatabaseManager {
    int[] queryIds(String tableName);
    int queryRandomId(String tableName);
    <T> T query(int id, String tableName);
}
