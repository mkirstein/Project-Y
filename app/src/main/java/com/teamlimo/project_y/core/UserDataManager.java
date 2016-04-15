package com.teamlimo.project_y.core;

/**
 * Created by Project0rion on 15.04.2016.
 */
public class UserDataManager {

    private static IUserDataManager impl;

    private UserDataManager() {

    }

    public static void init(IUserDataManager manager) {
        impl = manager;
    }

    public static IUserDataManager getInstance() {
        return impl;
    }
}
