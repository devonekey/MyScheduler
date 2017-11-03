package com.onekey.myscheduler.view.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.onekey.myscheduler.R;
import com.onekey.myscheduler.contract.MemoContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by OneKey on 2017-10-17.
 */

public class MemoDateViewHolder
        extends AbstractViewHolder<MemoContract.Presenter>
        implements MemoContract.DateViewHolder<MemoContract.Presenter> {
    @BindView(R.id.view_holder_memo_date_text_view)
    TextView dateTextView;

    public static final int TYPE = 2;

    public MemoDateViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind() {
        presenter.onBindViewHolder(this, getAdapterPosition());
    }

    @Override
    public void setDate(String date) {
        dateTextView.setText(date);
    }
}
