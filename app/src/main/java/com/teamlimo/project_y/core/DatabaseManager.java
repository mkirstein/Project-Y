package com.teamlimo.project_y.core;

/**
 * Created by Project0rion on 27.03.2016.
 */
public final class DatabaseManager {

    private static IDatabaseManager impl;

    private DatabaseManager() {
        
    }

    public static IDatabaseManager getInstance() {
        if (impl == null)
            impl = new HttpDatabaseManager("http://192.168.178.37/");

        return impl;
    }
}
