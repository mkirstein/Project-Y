package com.teamlimo.project_y.core;

/**
 * Created by Marc on 06.04.2016.
 */
public interface IPresenterFactory {

    <T> T getPresenter(Class<T> presenterType);
}
