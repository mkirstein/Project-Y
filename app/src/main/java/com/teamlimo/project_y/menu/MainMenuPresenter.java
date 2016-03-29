package com.teamlimo.project_y.menu;

import com.teamlimo.project_y.androidCore.AndroidViewManager;
import com.teamlimo.project_y.core.ViewManager;

/**
 * Created by Project0rion on 20.03.2016.
 */
public class MainMenuPresenter {

    private Object mainMenuView;

    public MainMenuPresenter(Object mainMenuView) {
        this.mainMenuView = mainMenuView;
        ViewManager.init(new AndroidViewManager());
    }

    public void startQuiz() {
        ViewManager.getInstance().switchView(mainMenuView, ViewManager.getInstance().getViewFactory().createQuizView());
    }

    public void viewHighscore() {
        ViewManager.getInstance().switchView(mainMenuView, ViewManager.getInstance().getViewFactory().createHighscoreView());
    }
}
