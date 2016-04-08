package com.example.administrator.gc.widget.LoadMoreRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2016/4/8.
 */
public class LoadMoreRecyclerView extends RecyclerView {


    private boolean isLoading = false;

    private ILoadMore iLoadMore;

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        LayoutManager manager = getLayoutManager();
        int position;
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            position = linearLayoutManager.findLastVisibleItemPosition();
            Log.d("position", "pos" + position + " " + getAdapter().getItemCount());
            if (position == this.getAdapter().getItemCount() && this.getScrollState() == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                if (iLoadMore != null) {
                    isLoading = true;
                    iLoadMore.loadMore();
                }
            }
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }


    public ILoadMore getLoadMore() {
        return iLoadMore;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.iLoadMore = iLoadMore;
    }
}
