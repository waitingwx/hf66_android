package com.cqxy.utils;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by PeLon on 2017/9/19.
 */

public class RecycleUtils {
    public static void initLinearLayoutRecyclerViews(RecyclerView recyclerView, Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    public static void initGridLayoutRecyclerViews(RecyclerView recyclerView, Context context, int column) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, column));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }
}
