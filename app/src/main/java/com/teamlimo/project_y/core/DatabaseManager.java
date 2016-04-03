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
            impl = new HttpDatabaseManager("http://lamp.wlan.hwr-berlin.de/CS/csdb2/");

        return impl;
    }
}
