package com.teamlimo.project_y.core;

import java.util.ArrayList;

/**
 * Created by Project0rion on 27.03.2016.
 */
public interface IDatabaseManager {

    <T> T queryPrimitive(Class<T> primitiveType, String operationName);
    <T extends IEntity> ArrayList<T> queryMany(Class<T> entityType, String operationName);
    void insertOrUpdate(String operationName, IEntity entity);
}
