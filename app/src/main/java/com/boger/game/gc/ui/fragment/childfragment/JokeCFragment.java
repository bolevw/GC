package com.boger.game.gc.ui.fragment.childfragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.JokeResponse;
import com.boger.game.gc.presenter.fragment.JokePresenter;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.boger.game.gc.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/3/22.
 */
public class JokeCFragment extends BaseFragment {
    private static final int TYPE_NORMAL = 0x0001;
    private static final int TYPE_LOADING = 0x0002;
    private static final int TYPE_NO_DATA = 0x0003;

    private JokePresenter presenter;
    private int index = 1;
    private boolean isLoading = false;

    @BindView(R.id.jokeRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.jokeSwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    private List<JokeResponse.ShowapiResBodyBean.JokeBean.ContentlistBean> viewData = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.child_joke_fragment;
    }

    @Override
    protected void initViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.setAdapter(new RVAdapter());
    }

    @Override
    protected void bind() {
        presenter = new JokePresenter(this);
        presenter.getData(index);
    }

    public void setViewData(JokeResponse response, int index) {
        if (index == 1) {
            viewData.clear();
        }
        viewData.addAll(response.getShowapi_res_body().getPagebean().getContentlist());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(onRefreshListener);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = manager.findLastVisibleItemPosition();
                if (position == viewData.size() && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    index++;
                    presenter.getData(index);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(index);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            index = 1;
            presenter.getData(index);
        }
    };

    public void stopRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_NORMAL) {
                return new VH(LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_joke, parent, false));
            } else if (viewType == TYPE_LOADING) {
                return new FootVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false));
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position < viewData.size()) {
                final JokeResponse.ShowapiResBodyBean.JokeBean.ContentlistBean model = viewData.get(position);
                VH vh = (VH) holder;
                vh.jokeTextView.setText(model.getText());
                vh.dateTextView.setText(model.getCreate_time());
                ImageLoaderUtils.load(model.getProfile_image(), vh.iv);
                vh.nameTv.setText(model.getName());
                vh.rootView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, model.getText());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setType("text/plain"); // 纯文本
                        intent.putExtra(Intent.EXTRA_SUBJECT, "ss");
                        startActivity(Intent.createChooser(intent, "分享"));
                        return true;
                    }
                });
            } else if (position == viewData.size()) {
                FootVh vh = (FootVh) holder;
            }
        }


        @Override
        public int getItemCount() {
            return viewData.size() == 0 ? 0 : viewData.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < viewData.size()) {
                return TYPE_NORMAL;
            } else if (position == viewData.size()) {
                return TYPE_LOADING;
            } else {
                return TYPE_NO_DATA;
            }
        }

        private class VH extends RecyclerView.ViewHolder {
            private TextView jokeTextView;
            private TextView dateTextView;
            private TextView nameTv;
            private CircleImageView iv;
            private LinearLayout rootView;

            public VH(View itemView) {
                super(itemView);
                nameTv = (TextView) itemView.findViewById(R.id.name);
                iv = (CircleImageView) itemView.findViewById(R.id.avatarIv);
                rootView = (LinearLayout) itemView.findViewById(R.id.rootView);
                jokeTextView = (TextView) itemView.findViewById(R.id.itemJokeTextView);
                dateTextView = (TextView) itemView.findViewById(R.id.itemTimeTextView);
            }
        }

        private class FootVh extends RecyclerView.ViewHolder {
            private LinearLayout loadingContent;
            private TextView noDataTextView;

            public FootVh(View itemView) {
                super(itemView);
                loadingContent = (LinearLayout) itemView.findViewById(R.id.loadingContent);
                noDataTextView = (TextView) itemView.findViewById(R.id.noDataTextView);
            }
        }
    }


    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
