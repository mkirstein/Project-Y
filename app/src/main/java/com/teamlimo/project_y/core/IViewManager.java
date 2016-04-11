package com.teamlimo.project_y.core;

import java.util.concurrent.Callable;

/**
 * Created by Project0rion on 27.03.2016.
 */
public interface IViewManager {

    IViewFactory getViewFactory();
    void switchView(Object sourceView, Object targetView);
    void showDialog(Object sourceView, int errorCode, boolean isCancelable, final Callable onConfirm, final Callable onRefuse);
}
