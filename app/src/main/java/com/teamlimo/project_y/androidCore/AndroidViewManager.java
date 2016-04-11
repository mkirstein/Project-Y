package com.teamlimo.project_y.androidCore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.teamlimo.project_y.R;
import com.teamlimo.project_y.core.IViewFactory;
import com.teamlimo.project_y.core.IViewManager;
import com.teamlimo.project_y.highscore.HighscoreActivity;
import com.teamlimo.project_y.menu.MainMenuActivity;
import com.teamlimo.project_y.quiz.QuizActivity;
import com.teamlimo.project_y.quizResult.QuizResultActivity;

import java.util.concurrent.Callable;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class AndroidViewManager implements IViewManager, IViewFactory {

    @Override
    public IViewFactory getViewFactory() {
        return this;
    }

    @Override
    public void switchView(Object sourceView, Object targetView) {
        Activity sourceActivity = sourceView instanceof Activity ? (Activity)sourceView : null;

        if (sourceActivity == null)
            return;

        Intent myIntent = new Intent(sourceActivity, targetView.getClass());
        sourceActivity.startActivity(myIntent);
    }

    @Override
    public void showDialog(Object sourceView, int errorCode, boolean isCancelable, final Callable onConfirm, final Callable onRefuse) {
        Activity sourceActivity = sourceView instanceof Activity ? (Activity)sourceView : null;

        ErrorCodeDialogInfo dialogInfo = ErrorCodeUtils.getErrorCodeDialogInfo(errorCode, sourceActivity);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(sourceActivity);
        alertDialogBuilder.setCancelable(isCancelable);
        alertDialogBuilder.setTitle(dialogInfo.getTitle());
        alertDialogBuilder.setMessage(dialogInfo.getMessage());
        alertDialogBuilder.setPositiveButton(dialogInfo.getPositiveChoice(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    onConfirm.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialogBuilder.setNeutralButton(dialogInfo.getNegativeChoice(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    onRefuse.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialogBuilder.create().show();
    }


    @Override
    public Object createMenuView() {
        return new MainMenuActivity();
    }

    @Override
    public Object createQuizView() {
        return new QuizActivity();
    }

    @Override
    public Object createQuizResultView() {
        return new QuizResultActivity();
    }

    @Override
    public Object createHighscoreView() {
        return new HighscoreActivity();
    }
}
