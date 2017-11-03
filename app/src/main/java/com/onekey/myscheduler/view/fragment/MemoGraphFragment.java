package com.onekey.myscheduler.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.onekey.myscheduler.R;
import com.onekey.myscheduler.contract.MemoContract;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by OneKey on 2017-10-26.
 */

public class MemoGraphFragment
        extends AbstractFragment<MemoContract.Presenter>
        implements MemoContract.GraphFragment<MemoContract.Presenter> {
    @BindView(R.id.fragment_memo_graph_bar_chart)
    BarChart barChart;

    private static MemoGraphFragment memoGraphFragment;

    public MemoGraphFragment() {

    }

    public static MemoGraphFragment getInstance() {
        if (memoGraphFragment == null) {
            memoGraphFragment = new MemoGraphFragment();
        }

        return memoGraphFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_graph, container, false);
        super.setView(view);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(100.0f, 0));
        entries.add(new BarEntry(50.0f, 1));
        entries.add(new BarEntry(75.0f, 2));
        entries.add(new BarEntry(50.0f, 3));

        BarDataSet barDataSet = new BarDataSet(entries, "Chart");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);

        barChart.setData(data);
        barChart.invalidate();

        return view;
    }
}
