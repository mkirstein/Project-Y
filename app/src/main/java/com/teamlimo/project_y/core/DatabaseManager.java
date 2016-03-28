package com.teamlimo.project_y.core;

/**
 * Created by Project0rion on 27.03.2016.
 */
public final class DatabaseManager {

    private static IDatabaseRequester impl;

    private DatabaseManager() {
        
    }

    public static IDatabaseRequester getInstance() {
        if (impl == null)
            impl = new HttpRequester();

        return impl;
    }
}
