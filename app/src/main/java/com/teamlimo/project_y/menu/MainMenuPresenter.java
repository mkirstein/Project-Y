package com.teamlimo.project_y.menu;

import com.teamlimo.project_y.androidCore.AndroidViewManager;
import com.teamlimo.project_y.core.ViewManager;

/**
 * Created by Project0rion on 20.03.2016.
 */
public class MainMenuPresenter {

    private IMainMenuView mainMenuView;

    public MainMenuPresenter(IMainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        ViewManager.init(new AndroidViewManager());
    }

    public void startQuiz() {
        ViewManager.getInstance().switchView(mainMenuView, mainMenuView.createQuizView());
    }

    public void viewHighscore() {
        ViewManager.getInstance().switchView(mainMenuView, mainMenuView.createHighscoreView());
    }
}
