package com.onekey.myscheduler.view.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.onekey.myscheduler.R;
import com.onekey.myscheduler.contract.MemoContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by OneKey on 2017-09-26.
 */

public class MemoContentViewHolder
        extends AbstractViewHolder<MemoContract.Presenter>
        implements MemoContract.MemoViewHolder<MemoContract.Presenter> {
    @BindView(R.id.view_holder_memo_content_created_at_title_text_view)
    TextView createdAtTitleTextView;
    @BindView(R.id.view_holder_memo_content_created_at_content_text_view)
    TextView createdAtContentTextView;
    @BindView(R.id.view_holder_memo_content_updated_at_title_text_view)
    TextView updatedAtTitleTextView;
    @BindView(R.id.view_holder_memo_content_updated_at_content_text_view)
    TextView updatedAtContentTextView;
    @BindView(R.id.view_holder_memo_content_title_text_view)
    TextView titleTextView;
    @BindView(R.id.view_holder_memo_content_description_text_view)
    TextView descriptionTextView;

    private OnItemClickListener onItemClickListener;

    public static final int TYPE = 1;

    public MemoContentViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind() {
        presenter.onBindViewHolder(this, getAdapterPosition());
    }

    @Override
    public void setCreatedAt(String createdAt) {
        if (createdAt != null) {
            createdAtTitleTextView.setVisibility(View.VISIBLE);
            createdAtContentTextView.setVisibility(View.VISIBLE);
            createdAtContentTextView.setText(createdAt);
        } else {
            createdAtTitleTextView.setVisibility(View.GONE);
            createdAtContentTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUpdatedAt(String updatedAt) {
        if (updatedAt != null) {
            updatedAtTitleTextView.setVisibility(View.VISIBLE);
            updatedAtContentTextView.setVisibility(View.VISIBLE);
            updatedAtContentTextView.setText(updatedAt);
        } else {
            updatedAtTitleTextView.setVisibility(View.GONE);
            updatedAtContentTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }

    @OnClick(R.id.view_holder_memo_layout)
    void onClick(View view) {
        onItemClickListener.onItemClick(view, getAdapterPosition());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
