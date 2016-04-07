package com.example.administrator.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.model.ForumItemDetailModel;
import com.example.administrator.gc.presenter.activity.ForumDetailListPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ForumDetailListActivity extends BaseActivity {

    ForumDetailListPresenter presenter;

    private String urls = "";


    private RecyclerView forumDetailRecyclerView;

    private ArrayList<ForumItemDetailModel> recyclerViewData = new ArrayList<>();


    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, ForumDetailListActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_forum_detail_list);
        Intent intent = getIntent();
        urls = intent.getStringExtra("urls");

        forumDetailRecyclerView = (RecyclerView) findViewById(R.id.forumDetailRecyclerView);
        forumDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void setListener() {
        forumDetailRecyclerView.setAdapter(new RVAdapter());
    }

    public void nofityChange(ArrayList<ForumItemDetailModel> list) {
        if (list.size() == 0) {
            ForumPostListActivity.newInstance(ForumDetailListActivity.this, urls);
            finish();
        } else {
            recyclerViewData.clear();
            recyclerViewData.addAll(list);
            forumDetailRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected void bind() {
        this.presenter = new ForumDetailListPresenter();
        this.presenter.bind(this);
        if (TextUtils.isEmpty(urls)) {
            return;
        }
        this.presenter.getData(urls);
    }

    @Override
    protected void unBind() {
        this.presenter.unBind();
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_detail_list_recelerview, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ForumItemDetailModel model = recyclerViewData.get(position);
            VH vh = (VH) holder;
            vh.name.setText(model.getName());
            vh.theme.setText(String.format(getString(R.string.theme_count), model.getThemeCount()));
            vh.post.setText(String.format(getString(R.string.post_count), model.getPostCount()));
            vh.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForumPostListActivity.newInstance(ForumDetailListActivity.this, model.getUrls());
                }
            });
        }

        @Override
        public int getItemCount() {
            return recyclerViewData.size();
        }

        private class VH extends RecyclerView.ViewHolder {
            private LinearLayout content;
            private TextView name;
            private TextView theme;
            private TextView post;


            public VH(View itemView) {
                super(itemView);
                content = (LinearLayout) itemView.findViewById(R.id.content);
                name = (TextView) itemView.findViewById(R.id.name);
                theme = (TextView) itemView.findViewById(R.id.themeCount);
                post = (TextView) itemView.findViewById(R.id.postCount);
            }
        }
    }
}
