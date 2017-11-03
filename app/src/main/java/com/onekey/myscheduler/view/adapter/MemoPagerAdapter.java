package com.onekey.myscheduler.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.onekey.myscheduler.contract.MemoContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OneKey on 2017-10-26.
 */

public class MemoPagerAdapter extends FragmentPagerAdapter implements MemoContract.PagerAdapter {
    private MemoContract.Presenter presenter;
    private List<Fragment> pages;

    public MemoPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        pages = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public void setPresenter(MemoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void add(Fragment fragment) {
        pages.add(fragment);
        notifyDataSetChanged();
    }
}
