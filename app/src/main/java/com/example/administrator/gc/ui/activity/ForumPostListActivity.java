package com.example.administrator.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.model.ForumPostListItemModel;
import com.example.administrator.gc.presenter.activity.ForumPostListPresenter;
import com.example.administrator.gc.widget.RecyclerViewCutLine;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumPostListActivity extends BaseActivity {


    ForumPostListPresenter presenter;
    private String urls = null;

    private RecyclerView recyclerView;
    private ArrayList<ForumPostListItemModel> viewData = new ArrayList<>();


    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, ForumPostListActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_forum_post_list);

        Intent intent = getIntent();
        urls = intent.getStringExtra("urls");

        recyclerView = (RecyclerView) findViewById(R.id.forumPostRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void notifyChange(ArrayList<ForumPostListItemModel> list) {
        viewData.clear();
        viewData.addAll(list);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        recyclerView.setAdapter(new RVAdapter());
        recyclerView.addItemDecoration(new RecyclerViewCutLine(getResources().getDimensionPixelSize(R.dimen.cut_line), 0));
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
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_post_list_recyclerview, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ForumPostListItemModel model = viewData.get(position);

            VH vh = (VH) holder;
            vh.title.setText(model.getName());
            vh.auth.setText(model.getAuthName());
            vh.date.setText(model.getDate());
            vh.commentCount.setText(model.getCommentCount());
            vh.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return viewData.size();
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
    }

}
