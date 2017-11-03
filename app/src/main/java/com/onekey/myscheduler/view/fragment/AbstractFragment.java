package com.onekey.myscheduler.view.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.onekey.myscheduler.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by OneKey on 2017-10-26.
 */

public abstract class AbstractFragment<T extends BasePresenter>
        extends Fragment
        implements BaseFragment<T> {
    protected T presenter;
    private Unbinder unbinder;

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }

        super.onDestroyView();
    }

    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    protected void setView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }
}
