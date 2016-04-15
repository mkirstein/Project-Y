package com.teamlimo.project_y.androidCore;

import android.content.Context;
import android.content.SharedPreferences;

import com.teamlimo.project_y.core.IUserDataManager;
import com.teamlimo.project_y.core.UserData;

/**
 * Created by Project0rion on 15.04.2016.
 */
public class AndroidUserDataManager implements IUserDataManager {

    private static final String USER_NAME_KEY = "user_name";

    @Override
    public void save(UserData userData) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_NAME_KEY, userData.getUserName());
        editor.commit();
    }

    @Override
    public UserData load() {
        SharedPreferences sharedPreferences = getSharedPreferences();

        UserData loadedData = new UserData();
        loadedData.setUserName(sharedPreferences.getString(USER_NAME_KEY, null));

        return loadedData;
    }

    private SharedPreferences getSharedPreferences() {
        final String SHARED_PREFERENCES_KEY = "project_y_shared_preferences";
        return Application.getContext().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}
