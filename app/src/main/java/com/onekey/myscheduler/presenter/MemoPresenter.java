package com.onekey.myscheduler.presenter;

import android.content.Context;

import com.onekey.myscheduler.contract.MemoContract;
import com.onekey.myscheduler.data.Memo;
import com.onekey.myscheduler.data.MemoContent;
import com.onekey.myscheduler.data.source.MemoDataRepository;
import com.onekey.myscheduler.data.source.MemoDataSource;
import com.onekey.myscheduler.data.source.local.MemoLocalDataSource;
import com.onekey.myscheduler.view.activity.AddEditMemoActivity;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by OneKey on 2017-09-26.
 */

public class MemoPresenter extends AbstractPresenter implements MemoContract.Presenter {
    private static MemoPresenter memoPresenter;
    private MemoDataRepository memoDataRepository;
    private MemoContract.Activity activity;
    private MemoContract.PagerAdapter pagerAdapter;
    private MemoContract.ListFragment listFragment;
    private MemoContract.GraphFragment graphFragment;
    private MemoContract.ListAdapter listAdapter;

    private MemoPresenter(Context context) {
        memoDataRepository = MemoDataRepository.getInstance(MemoLocalDataSource.getInstance(context));
    }

    public static MemoPresenter getInstance(Context context) {
        if (memoPresenter == null) {
            memoPresenter = new MemoPresenter(context);
        }
        return memoPresenter;
    }

    @Override
    public void setActivity(MemoContract.Activity activity) {
        if (this.activity == null) {
            this.activity = activity;
            this.activity.setPresenter(this);
        }
    }

    @Override
    public void setPagerAdapter(MemoContract.PagerAdapter pagerAdapter) {
        if (this.pagerAdapter == null) {
            this.pagerAdapter = pagerAdapter;
            this.pagerAdapter.setPresenter(this);
        }
    }

    @Override
    public void setListFragment(MemoContract.ListFragment listFragment) {
        if (this.listFragment == null) {
            this.listFragment = listFragment;
            this.listFragment.setPresenter(this);
        }
    }

    @Override
    public void setGraphFragment(MemoContract.GraphFragment graphFragment) {
        if (this.graphFragment == null) {
            this.graphFragment = graphFragment;
            this.graphFragment.setPresenter(this);
        }
    }

    public void setListAdapter(MemoContract.ListAdapter listAdapter) {
        if (this.listAdapter == null) {
            this.listAdapter = listAdapter;
            this.listAdapter.setPresenter(this);
        }
    }

    @Override
    public void start() {
        memoDataRepository.getMemos(new MemoDataSource.LoadMemosCallback() {
            @Override
            public void onLoadSuccess(List<Memo> memos) {
                listAdapter.notifyDataSetChanged();
                activity.hideProgressDialog();
            }

            @Override
            public void onLoading() {
                activity.showProgressDialog();
            }

            @Override
            public void onLoadFailure() {
                activity.hideProgressDialog();
            }
        });
    }

    @Override
    public void end() {
        if (listAdapter != null) {
            listAdapter = null;
        }
        if (listFragment != null) {
            listFragment = null;
        }
        if (pagerAdapter != null) {
            pagerAdapter = null;
        }
        if (activity != null) {
            activity = null;
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (requestCode == AddEditMemoActivity.ADD_MEMO_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            listAdapter.notifyDataSetChanged();
            listFragment.smoothScrollToFirst();
        } else if (requestCode == AddEditMemoActivity.EDIT_MEMO_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void prev() {
        // ignore this block
    }

    @Override
    public void next() {
        memoDataRepository.getMemos(new MemoDataSource.LoadMemosCallback() {
            @Override
            public void onLoadSuccess(List<Memo> memos) {
                listAdapter.notifyDataSetChanged();
                activity.hideProgressDialog();

            }

            @Override
            public void onLoading() {
                activity.showProgressDialog();
            }

            @Override
            public void onLoadFailure() {
                activity.hideProgressDialog();
            }
        });
    }

    @Override
    public void onBindViewHolder(MemoContract.MemoViewHolder memoViewHolder, int position) {
        memoDataRepository.getMemo(position, new MemoDataSource.LoadMemoCallback() {
            @Override
            public void onLoadSuccess(Memo memo) {
                memoViewHolder.setCreatedAt(((MemoContent) memo).getCreatedAt());
                memoViewHolder.setUpdatedAt(((MemoContent) memo).getUpdatedAt());
                memoViewHolder.setTitle(((MemoContent) memo).getTitle());
                memoViewHolder.setDescription(((MemoContent) memo).getDescription());
            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailure() {

            }
        });
    }

    @Override
    public void onBindViewHolder(MemoContract.DateViewHolder dateViewHolder, int position) {
        memoDataRepository.getMemo(position, new MemoDataSource.LoadMemoCallback() {
            @Override
            public void onLoadSuccess(Memo memo) {
                dateViewHolder.setDate(memo.getDenotedDate());
            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailure() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return memoDataRepository.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return memoDataRepository.getItemType(position);
    }
}
