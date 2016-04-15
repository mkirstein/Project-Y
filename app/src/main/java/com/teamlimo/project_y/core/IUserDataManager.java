package com.teamlimo.project_y.core;

/**
 * Created by Project0rion on 15.04.2016.
 */
public interface IUserDataManager {

    void save(UserData userData);
    UserData load();
}
