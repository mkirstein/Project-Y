package com.teamlimo.project_y.core;

/**
 * Created by Project0rion on 27.03.2016.
 */
public interface IViewManager {

    IViewFactory getViewFactory();
    void switchView(Object sourceView, Object targetView);
}
