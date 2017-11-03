package com.onekey.myscheduler.view.activity;

import com.onekey.myscheduler.presenter.BasePresenter;
import com.onekey.myscheduler.view.BaseView;

/**
 * Created by OneKey on 2017-10-19.
 */

public interface BaseActivity<T extends BasePresenter> extends BaseView<T> {
    void showProgressDialog();
    void hideProgressDialog();
}
