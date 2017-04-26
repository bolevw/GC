package com.boger.game.gc.widget.loadmoreRecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by liubo on 2017/4/21.
 */

public class LoadingMoreRv extends RecyclerView {

    public LoadingMoreRv(Context context) {
        super(context);
    }

    public LoadingMoreRv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingMoreRv(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        LayoutManager manager = getLayoutManager();
        Adapter adapter = getAdapter();
        int lastPosition = adapter.getItemCount();

        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            lastPosition = linearLayoutManager.findLastVisibleItemPosition();
        } else if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            lastPosition = gridLayoutManager.findLastVisibleItemPosition();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            int size = staggeredGridLayoutManager.getSpanCount();
            int[] result = new int[size];
            staggeredGridLayoutManager.findLastVisibleItemPositions(result);
            lastPosition = result[size - 1];
        }
        Log.d("TestListActivity", "onScrollChanged() called with: l = " + lastPosition);

        if (lastPosition == adapter.getItemCount() - 1 && state == RecyclerView.SCROLL_STATE_IDLE) {
            if (listener != null) {
                listener.load();
            }
        }
    }

    private LoadListener listener;

    public interface LoadListener {
        void load();
    }

    public void setListener(LoadListener listener) {
        this.listener = listener;
    }
}
