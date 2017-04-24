package com.boger.game.gc.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liubo on 2017/4/23.
 */

public class MyLayoutManager extends LinearLayoutManager {
    private View header;

    public MyLayoutManager(Context context) {
        super(context);
    }

    public MyLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (header == null) {
            header = recycler.getViewForPosition(0);
            if (header != null) {
                header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            }
        }
    }
}
