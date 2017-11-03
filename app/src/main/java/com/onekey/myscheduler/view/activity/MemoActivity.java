package com.onekey.myscheduler.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.onekey.myscheduler.R;
import com.onekey.myscheduler.contract.MemoContract;
import com.onekey.myscheduler.etc.ConstantsEntry;
import com.onekey.myscheduler.presenter.MemoPresenter;
import com.onekey.myscheduler.view.adapter.MemoPagerAdapter;
import com.onekey.myscheduler.view.fragment.MemoGraphFragment;
import com.onekey.myscheduler.view.fragment.MemoListFragment;

import butterknife.BindView;

public class MemoActivity
        extends AbstractActivity<MemoContract.Presenter>
        implements MemoContract.Activity<MemoContract.Presenter>, ConstantsEntry {
    @BindView(R.id.activity_memos_view_pager)
    ViewPager viewPager;

    private MemoPagerAdapter adapter;
    private MemoListFragment memoListFragment;
    private MemoGraphFragment memoGraphFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setView(R.layout.activity_memos);

        memoListFragment = MemoListFragment.getInstance();
        memoGraphFragment = MemoGraphFragment.getInstance();

        adapter = new MemoPagerAdapter(getSupportFragmentManager());
        adapter.add(memoListFragment);
        adapter.add(memoGraphFragment);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);

        presenter = MemoPresenter.getInstance(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        presenter.result(requestCode, resultCode);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setActivity(this);
        presenter.setPagerAdapter(adapter);
        presenter.setListFragment(memoListFragment);
        presenter.setGraphFragment(memoGraphFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.end();
    }
}
