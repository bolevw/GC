package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.model.ForumPostListItemModel;
import com.boger.game.gc.presenter.activity.ForumPostListPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumListActivity extends BaseSwipeBackActivity {

    private static final int TYPE_LOADING = 0x0001;
    private static final int TYPE_EMPTY = 0x0002;
    private static final int TYPE_NORMAL = 0x0003;

    private ForumPostListPresenter presenter;
    private String urls = null;
    private boolean isLoading = false;
    private RecyclerView recyclerView;
    private ArrayList<ForumPostListItemModel> viewData = new ArrayList<>();

    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, ForumListActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forum_post_list;
    }

    @Override
    protected void initViewData() {
        Intent intent = getIntent();
        urls = intent.getStringExtra("urls");

        recyclerView = (RecyclerView) findViewById(R.id.forumPostRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void notifyChange(ArrayList<ForumPostListItemModel> list) {
        viewData.addAll(list);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        recyclerView.setAdapter(new RVAdapter());
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    protected void bind() {
        this.presenter = new ForumPostListPresenter();
        this.presenter.bind(this);
        this.presenter.getData(urls);
    }

    @Override
    protected void unBind() {
        this.presenter.unBind();
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_NORMAL) {
                return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_post_list_recyclerview, parent, false));
            } else if (viewType == TYPE_LOADING) {
                return new FootVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (viewData.size() != 0 && position < viewData.size()) {
                final ForumPostListItemModel model = viewData.get(position);

                VH vh = (VH) holder;
                vh.title.setText(model.getName());
                vh.auth.setText(model.getAuthName());
                vh.date.setText(model.getDate());
                vh.commentCount.setText(model.getCommentCount());
                vh.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PostDetailActivity.newInstance(ForumListActivity.this, model.getUrls());
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (viewData.size() == 0) {
                return TYPE_EMPTY;
            } else if (position == viewData.size()) {
                return TYPE_LOADING;
            } else {
                return TYPE_NORMAL;
            }
        }

        @Override
        public int getItemCount() {
            return viewData.size() + 1;
        }

        private class VH extends RecyclerView.ViewHolder {

            private LinearLayout content;
            private TextView title;
            private TextView auth;
            private TextView date;
            private TextView commentCount;


            public VH(View itemView) {
                super(itemView);

                content = (LinearLayout) itemView.findViewById(R.id.content);
                title = (TextView) itemView.findViewById(R.id.postTitle);
                auth = (TextView) itemView.findViewById(R.id.postAuth);
                date = (TextView) itemView.findViewById(R.id.postDate);
                commentCount = (TextView) itemView.findViewById(R.id.postComment);
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

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager manager = ForumListActivity.this.recyclerView.getLayoutManager();
            int position;
            if (manager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
                position = linearLayoutManager.findLastVisibleItemPosition();
                if (position == viewData.size() && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {

                    isLoading = true;
                    presenter.getMore();

                }
            }
        }
    };


    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }
}
