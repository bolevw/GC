package com.example.administrator.gc.widget.LoadMoreRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_LOADING = 0x000001;
    public static final int TYPE_EMPTY = 0x0000002;
    public static final int TYPE_NORMAL = 0x0000003;

    private List data;

    public LoadMoreAdapter(List data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_LOADING) {
            return new FootVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == data.size()) {
            FootVh vh = (FootVh) holder;
            if (hasData) {
                vh.loadingContent.setVisibility(View.VISIBLE);
                vh.noDataTextView.setVisibility(View.GONE);
            } else {
                vh.loadingContent.setVisibility(View.GONE);
                vh.noDataTextView.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() == 0) {
            return TYPE_EMPTY;
        } else if (data.size() != 0 && position == data.size()) {
            return TYPE_LOADING;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    private boolean hasData;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
        notifyItemChanged(data.size());
    }


    class FootVh extends RecyclerView.ViewHolder {
        private LinearLayout loadingContent;
        private TextView noDataTextView;

        public FootVh(View itemView) {
            super(itemView);
            loadingContent = (LinearLayout) itemView.findViewById(R.id.loadingContent);
            noDataTextView = (TextView) itemView.findViewById(R.id.noDataTextView);
        }
    }

}
