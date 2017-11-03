package com.onekey.myscheduler.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.onekey.myscheduler.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by OneKey on 2017-10-19.
 */

abstract class AbstractViewHolder<T extends BasePresenter> extends RecyclerView.ViewHolder implements BaseViewHolder<T> {
    protected T presenter;

    AbstractViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public abstract void bind();
}
