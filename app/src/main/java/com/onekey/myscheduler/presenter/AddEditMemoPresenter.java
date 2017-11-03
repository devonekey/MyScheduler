package com.onekey.myscheduler.presenter;

import android.content.Context;

import com.onekey.myscheduler.contract.AddEditMemoContract;
import com.onekey.myscheduler.data.Memo;
import com.onekey.myscheduler.data.MemoContent;
import com.onekey.myscheduler.data.source.MemoDataRepository;
import com.onekey.myscheduler.data.source.MemoDataSource;
import com.onekey.myscheduler.data.source.local.MemoLocalDataSource;

/**
 * Created by OneKey on 2017-09-26.
 */

public class AddEditMemoPresenter extends AbstractPresenter implements AddEditMemoContract.Presenter {
    private static AddEditMemoPresenter addEditMemoPresenter;
    private MemoDataRepository memoDataRepository;
    private AddEditMemoContract.Activity activity;
    private int position;

    private AddEditMemoPresenter(Context context) {
        memoDataRepository = MemoDataRepository.getInstance(MemoLocalDataSource.getInstance(context));
    }

    public static AddEditMemoPresenter getInstance(Context context) {
        if (addEditMemoPresenter == null) {
            addEditMemoPresenter = new AddEditMemoPresenter(context);
        }
        return addEditMemoPresenter;
    }

    @Override
    public void setActivity(AddEditMemoContract.Activity activity) {
        if (this.activity == null) {
            this.activity = activity;
            this.activity.setPresenter(this);
        }
    }

    @Override
    public void start() {
        memoDataRepository.getMemo(position, new MemoDataSource.LoadMemoCallback() {
            @Override
            public void onLoadSuccess(Memo memo) {
                activity.setTitle(((MemoContent) memo).getTitle());
                activity.setDescription(((MemoContent) memo).getDescription());
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
        if (activity != null) {
            activity = null;
        }
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void save(String title, String description) {
        memoDataRepository.putMemo(position, new MemoContent(title, description), new MemoDataSource.SaveMemoCallback() {
            @Override
            public void onSaveSuccess() {
                activity.successfullySaved();
                activity.hideProgressDialog();
            }

            @Override
            public void onSaving() {
                activity.showProgressDialog();
            }

            @Override
            public void onSaveFailure() {
                activity.hideProgressDialog();
            }
        });
    }
}
