package com.teamlimo.project_y.core;

import java.util.Hashtable;

/**
 * Created by Marc on 06.04.2016.
 */
public class PresenterFactory {

    private static PresenterFactory instance;
    private Hashtable<Class, Object> presenterTable;

    private PresenterFactory() {
        presenterTable = new Hashtable<>();
    }

    public static PresenterFactory getInstance() {
        if(instance == null) {
            instance = new PresenterFactory();
        }
        return instance;
    }

    public <T> T getPresenter(Class<T> presenterType) {
        if(!presenterTable.containsKey(presenterType)) {
            try {
                presenterTable.put(presenterType, presenterType.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) presenterTable.get(presenterType);
    }
}
