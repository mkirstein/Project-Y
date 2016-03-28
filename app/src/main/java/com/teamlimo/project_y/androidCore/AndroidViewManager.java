package com.teamlimo.project_y.androidCore;

import android.app.Activity;
import android.content.Intent;

import com.teamlimo.project_y.core.IViewManager;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class AndroidViewManager implements IViewManager {

    @Override
    public void switchView(Object sourceView, Object targetView) {
        Activity sourceActivity = sourceView instanceof Activity ? (Activity)sourceView : null;

        if (sourceActivity == null)
            return;

        Intent myIntent = new Intent(sourceActivity, targetView.getClass());
        sourceActivity.startActivity(myIntent);
    }
}
