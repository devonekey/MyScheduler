package com.onekey.myscheduler.contract;

import com.onekey.myscheduler.presenter.BasePresenter;
import com.onekey.myscheduler.view.activity.BaseActivity;

/**
 * Created by OneKey on 2017-09-26.
 */

public interface AddEditMemoContract {
    interface Activity<T extends Presenter> extends BaseActivity<T> {
        void setTitle(String title);
        void setDescription(String description);
        void successfullySaved();
    }

    interface Presenter extends BasePresenter {
        void setActivity(Activity activity);
        void setPosition(int position);
        void save(String title, String description);
    }
}
