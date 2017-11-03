package com.onekey.myscheduler.view.activity;

import android.app.ProgressDialog;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.onekey.myscheduler.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by OneKey on 2017-10-15.
 */

abstract class AbstractActivity<T extends BasePresenter>
        extends AppCompatActivity
        implements BaseActivity<T> {
    protected T presenter;
    private Unbinder unbinder;
    private ProgressDialog progressDialog;
    private boolean showing = false;

    @Override
    protected void onResume() {
        super.onResume();

        if (progressDialog != null && showing && !isFinishing()) {
            runOnUiThread(() -> progressDialog.show());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        hideProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void setPresenter(T presenter) {
        if (this.presenter == null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null && !isFinishing()) {
            showing = true;
            runOnUiThread(() -> progressDialog = ProgressDialog.show(this, "", "잠시만 기다려 주세요.", true));
        } else if (!progressDialog.isShowing() && !isFinishing()) {
            showing = true;
            runOnUiThread(() -> progressDialog.show());
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            showing = false;
            progressDialog.dismiss();
        }
    }

    protected void setView(@LayoutRes int resId) {
        setContentView(resId);

        unbinder = ButterKnife.bind(this);
    }
}
