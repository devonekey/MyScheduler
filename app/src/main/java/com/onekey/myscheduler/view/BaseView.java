package com.onekey.myscheduler.view;

import com.onekey.myscheduler.presenter.BasePresenter;

/**
 * Created by OneKey on 2017-09-26.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
