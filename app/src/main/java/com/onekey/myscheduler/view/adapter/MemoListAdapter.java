package com.onekey.myscheduler.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onekey.myscheduler.R;
import com.onekey.myscheduler.contract.MemoContract;
import com.onekey.myscheduler.view.adapter.holder.MemoContentViewHolder;
import com.onekey.myscheduler.view.adapter.holder.MemoDateViewHolder;

/**
 * Created by OneKey on 2017-09-26.
 */

public class MemoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MemoContract.ListAdapter {
    private MemoContract.Presenter presenter;
    private MemoContentViewHolder.OnItemClickListener onItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case MemoContentViewHolder.TYPE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_memo_content, parent, false);
                holder = new MemoContentViewHolder(itemView);

                ((MemoContentViewHolder) holder).setPresenter(presenter);
                ((MemoContentViewHolder) holder).setOnItemClickListener((view, position) -> onItemClickListener.onItemClick(view, position));
                break;
            case MemoDateViewHolder.TYPE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_memo_date, parent, false);
                holder = new MemoDateViewHolder(itemView);

                ((MemoDateViewHolder) holder).setPresenter(presenter);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        switch (getItemViewType(position)) {
            case MemoContentViewHolder.TYPE:
                ((MemoContentViewHolder) holder).bind();
                break;
            case MemoDateViewHolder.TYPE:
                ((MemoDateViewHolder) holder).bind();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getItemViewType(position);
    }

    @Override
    public void setPresenter(MemoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setOnItemClickListener(MemoContentViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
