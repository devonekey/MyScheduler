package com.onekey.myscheduler.view.fragment;

import com.onekey.myscheduler.presenter.BasePresenter;
import com.onekey.myscheduler.view.BaseView;

/**
 * Created by OneKey on 2017-10-26.
 */

public interface BaseFragment<T extends BasePresenter> extends BaseView<T> {
    void showProgressDialog();
    void hideProgressDialog();
}
