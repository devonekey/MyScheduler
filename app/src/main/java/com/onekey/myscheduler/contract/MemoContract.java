package com.onekey.myscheduler.contract;

import com.onekey.myscheduler.presenter.BasePresenter;
import com.onekey.myscheduler.view.BaseView;
import com.onekey.myscheduler.view.activity.BaseActivity;
import com.onekey.myscheduler.view.adapter.holder.BaseViewHolder;
import com.onekey.myscheduler.view.fragment.BaseFragment;

/**
 * Created by OneKey on 2017-09-26.
 */

public interface MemoContract {
    interface Activity<T extends Presenter> extends BaseActivity<T> {

    }

    interface PagerAdapter extends BaseView<Presenter> {

    }

    interface ListFragment<T extends Presenter> extends BaseFragment<T> {
        void smoothScrollToFirst();
    }

    interface GraphFragment<T extends Presenter> extends BaseFragment<T> {

    }

    interface ListAdapter extends BaseView<Presenter> {
        void notifyDataSetChanged();
    }

    interface MemoViewHolder<T extends Presenter> extends BaseViewHolder<T> {
        void setTitle(String title);
        void setDescription(String description);
        void setCreatedAt(String createdAt);
        void setUpdatedAt(String updatedAt);
    }

    interface DateViewHolder<T extends Presenter> extends BaseViewHolder<T> {
        void setDate(String date);
    }

    interface Presenter extends BasePresenter {
        void setActivity(Activity activity);
        void setPagerAdapter(PagerAdapter pagerAdapter);
        void setListFragment(ListFragment listFragment);
        void setGraphFragment(GraphFragment graphFragment);
        void setListAdapter(ListAdapter listAdapter);
        void result(int requestCode, int resultCode);
        void prev();
        void next();
        void onBindViewHolder(MemoViewHolder memoViewHolder, int position);
        void onBindViewHolder(DateViewHolder dateViewHolder, int position);
        int getItemCount();
        int getItemViewType(int position);
    }
}
