package com.boger.game.gc.widget.loadmoreRecyclerview;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boger.game.gc.R;


/**
 * Created by liubo on 2017/4/21.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 10000;
    private static final int TYPE_LOADING = 10001;
    private static final int TYPE_WRONG = 10002;
    private Callback callback;

    public BaseAdapter() {
    }

    public BaseAdapter(Callback callback) {
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            return new EmptyVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false));
        } else if (viewType == TYPE_LOADING) {
            return new LoadingVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false));
        } else if (viewType == TYPE_WRONG) {
            return new WrongVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_wrong, parent, false));
        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_LOADING) {
            initLoading((LoadingVh) holder);
        } else if (getItemViewType(position) == TYPE_WRONG) {
            initWrong((WrongVh) holder);
        } else if (getItemViewType(position) == TYPE_EMPTY) {
            initEmpty((EmptyVh) holder);
        } else {
            onBindItemVh(holder, position);
        }
    }

    private void initEmpty(EmptyVh holder) {
        EmptyVh vh = holder;
        vh.tv.setText(getEmptyText());
    }

    private void initWrong(WrongVh holder) {
        WrongVh vh = holder;
        vh.retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.retry();
            }
        });
    }

    private void initLoading(LoadingVh holder) {
        LoadingVh vh = holder;
        if (callback != null && callback.hasMoreData()) {
            vh.progressBar.setVisibility(View.VISIBLE);
            vh.tv.setText("loading...");
        } else {
            vh.progressBar.setVisibility(View.GONE);
            vh.tv.setText("end...");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (callback != null && callback.loadWrong()) {
            return TYPE_WRONG;
        } else {
            if (dataSize() == 0) {
                return TYPE_EMPTY;
            } else if (dataSize() == position) {
                return TYPE_LOADING;
            } else {
                return getItemType(position);
            }
        }
    }


    @Override
    public int getItemCount() {
        return dataSize() + 1;
    }

    protected abstract void onBindItemVh(RecyclerView.ViewHolder holder, int position);

    protected abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected abstract int getItemType(int position);

    protected abstract String getEmptyText();

    protected abstract int dataSize();

    class EmptyVh extends RecyclerView.ViewHolder {
        TextView tv;

        public EmptyVh(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.empty);
        }
    }

    class LoadingVh extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView tv;

        public LoadingVh(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            tv = (TextView) itemView.findViewById(R.id.noDataTextView);
        }
    }

    class WrongVh extends RecyclerView.ViewHolder {
        Button retry;

        public WrongVh(View itemView) {
            super(itemView);
            retry = (Button) itemView.findViewById(R.id.reLoadButton);
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        boolean loadWrong();

        boolean hasMoreData();

        void retry();
    }

    protected abstract class Vh extends RecyclerView.ViewHolder {
        private SparseArray<View> viewSparseArray = new SparseArray<>();
        private View container;

        public Vh(View itemView) {
            super(itemView);
            this.container = itemView;
        }

        public <V extends View> V getView(@IdRes int res) {
            return retrieveView(res);
        }

        protected <V extends View> V retrieveView(@IdRes int res) {
            View view = viewSparseArray.get(res);
            if (view == null) {
                view = container.findViewById(res);
                viewSparseArray.put(res, view);
            }
            return (V) view;
        }

        public void setText(@IdRes int id, String value) {
            TextView tv = getView(id);
            tv.setText(value);
        }
    }
}
