package com.teamlimo.project_y.core;

/**
 * Created by Project0rion on 27.03.2016.
 */
public final class ViewManager {

    private static IViewManager impl;

    private ViewManager() {

    }

    public static void init(IViewManager vm) {
        impl = vm;
    }

    public static IViewManager getInstance() {
        return impl;
    }
}
