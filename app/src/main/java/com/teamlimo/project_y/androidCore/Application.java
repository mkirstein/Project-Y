package com.teamlimo.project_y.androidCore;

import android.content.Context;

/**
 * Created by Project0rion on 15.04.2016.
 */
public class Application extends android.app.Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getContext() {
        return appContext;
    }
}
