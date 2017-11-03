package com.onekey.myscheduler.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onekey.myscheduler.R;
import com.onekey.myscheduler.contract.MemoContract;
import com.onekey.myscheduler.etc.ConstantsEntry;
import com.onekey.myscheduler.presenter.MemoPresenter;
import com.onekey.myscheduler.view.activity.AddEditMemoActivity;
import com.onekey.myscheduler.view.adapter.MemoListAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by OneKey on 2017-10-26.
 */

public class MemoListFragment
        extends AbstractFragment<MemoContract.Presenter>
        implements MemoContract.ListFragment<MemoContract.Presenter>, ConstantsEntry {
    @BindView(R.id.fragment_memo_list_recycler_view)
    RecyclerView recyclerView;

    private int direction = NOTHING_DIRECTION;

    private static final int NOTHING_DIRECTION = 0;
    private static final int UPWARD_DIRECTION = 1;
    private static final int DOWNWARD_DIRECTION = 2;

    public MemoListFragment() {

    }

    public static MemoListFragment getInstance() {
        Bundle bundle = new Bundle();

        MemoListFragment memoListFragment = new MemoListFragment();
        memoListFragment.setArguments(bundle);

        return memoListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = MemoPresenter.getInstance(getActivity().getBaseContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);
        super.setView(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        MemoListAdapter adapter = new MemoListAdapter();
        adapter.setOnItemClickListener((_view, position) -> {
            Intent intent = new Intent(getActivity(), AddEditMemoActivity.class);
            intent.putExtra(CLICKED_ITEM_POSITION, position);
            getActivity().startActivityForResult(intent, AddEditMemoActivity.EDIT_MEMO_TASK_REQUEST_CODE);
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (velocityY > 0) {
                    direction = DOWNWARD_DIRECTION;
                } else if (velocityY < 0) {
                    direction = UPWARD_DIRECTION;
                } else {
                    direction = NOTHING_DIRECTION;
                }

                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_SETTLING
                        && layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition() >= layoutManager.getItemCount()
                        && direction == DOWNWARD_DIRECTION) {
                    presenter.next();
                }
                /*else if (newState == RecyclerView.SCROLL_STATE_SETTLING
                        && layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition() >= layoutManager.getItemCount()
                        && direction == UPWARD_DIRECTION) {
                    presenter.prev();
                }*/

                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    direction = NOTHING_DIRECTION;
                }
            }
        });

        presenter.setListAdapter(adapter);
        presenter.start();

        return view;
    }

    @Override
    public void smoothScrollToFirst() {
        recyclerView.smoothScrollToPosition(FIRST_POSITION);
    }

    @OnClick(R.id.fragment_memo_list_add_button)
    void onClick(View view) {
        Intent intent = new Intent(getActivity(), AddEditMemoActivity.class);
        getActivity().startActivityForResult(intent, AddEditMemoActivity.ADD_MEMO_TASK_REQUEST_CODE);
    }
}
