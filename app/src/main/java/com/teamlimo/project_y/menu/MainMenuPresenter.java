package com.teamlimo.project_y.menu;

import com.teamlimo.project_y.androidCore.AndroidUserDataManager;
import com.teamlimo.project_y.androidCore.AndroidViewManager;
import com.teamlimo.project_y.core.ErrorCodes;
import com.teamlimo.project_y.core.PresenterFactory;
import com.teamlimo.project_y.core.UserDataManager;
import com.teamlimo.project_y.core.ViewManager;
import com.teamlimo.project_y.quiz.QuizPresenter;

import java.util.concurrent.Callable;

/**
 * Created by Project0rion on 20.03.2016.
 */
public class MainMenuPresenter {

    private Object view;

    public MainMenuPresenter(Object mainMenuView) {
        this.view = mainMenuView;
        ViewManager.init(new AndroidViewManager());
        UserDataManager.init(new AndroidUserDataManager());
    }

    public void startQuiz() {
        final QuizPresenter quizPresenter = PresenterFactory.getInstance().getPresenter(QuizPresenter.class);

        if (quizPresenter.hasActiveQuiz()) {
            ViewManager.getInstance().showDialog(view, ErrorCodes.REMAINING_QUIZ, true,
                    new Callable() {
                        @Override
                        public Object call() throws Exception {
                            // resume quiz
                            ViewManager.getInstance().switchView(view, ViewManager.getInstance().getViewFactory().createQuizView());
                            return null;
                        }
                    },
                    new Callable() {
                        @Override
                        public Object call() throws Exception {
                            // start new quiz
                            quizPresenter.reset();
                            ViewManager.getInstance().switchView(view, ViewManager.getInstance().getViewFactory().createQuizView());
                            return null;
                        }
                    });
        } else {
            ViewManager.getInstance().switchView(view, ViewManager.getInstance().getViewFactory().createQuizView());
        }
    }

    public void viewHighscore() {
        ViewManager.getInstance().switchView(view, ViewManager.getInstance().getViewFactory().createHighscoreView());
    }
}
