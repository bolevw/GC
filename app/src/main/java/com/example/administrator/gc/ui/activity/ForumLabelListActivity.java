package com.example.administrator.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.base.BaseModel;
import com.example.administrator.gc.base.ItemData;
import com.example.administrator.gc.model.ForumItemDetailModel;
import com.example.administrator.gc.model.ForumPartitionModel;
import com.example.administrator.gc.model.VideoModel;
import com.example.administrator.gc.presenter.activity.ForumDetailListPresenter;
import com.example.administrator.gc.utils.PicassoUtils;

import java.util.ArrayList;

/**
 * 论坛标签
 * Created by Administrator on 2016/4/6.
 */
public class ForumLabelListActivity extends BaseActivity {

    private static final int TYPE_VIDEO = 0x0001;
    private static final int TYPE_PARTITION = 0x0002;

    private ForumDetailListPresenter presenter;

    private String urls = "";

    private RecyclerView forumDetailRecyclerView;
    private TextView toolbarTitle;
    private ImageView bgImageView;

    private ArrayList<ItemData<Integer, BaseModel>> recyclerViewData = new ArrayList<>();

    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, ForumLabelListActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_forum_detail_list);
        Intent intent = getIntent();
        urls = intent.getStringExtra("urls");

        bgImageView = (ImageView) findViewById(R.id.forumPicImageView);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);

        forumDetailRecyclerView = (RecyclerView) findViewById(R.id.forumDetailRecyclerView);
        forumDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void setListener() {
        forumDetailRecyclerView.setAdapter(new RVAdapter());
    }

    public void notifyChange(ForumPartitionModel model) {
        toolbarTitle.setText(model.getTitle());

        String imgSrc = model.getImgSrc();
        if (model.getVideoList().size() > 0) {
            imgSrc = model.getVideoList().get(0).getImgSrc();
        }
        PicassoUtils.normalShowImage(imgSrc, bgImageView);

        if (model.getList().size() == 0 && model.getVideoList().size() == 0) {
            ForumListActivity.newInstance(ForumLabelListActivity.this, urls);
            finish();
        } else {
            recyclerViewData.clear();
            for (VideoModel videoModel : model.getVideoList()) {
                recyclerViewData.add(new ItemData<Integer, BaseModel>(TYPE_VIDEO, videoModel));
            }
            for (ForumItemDetailModel forumItemDetailModel : model.getList()) {
                recyclerViewData.add(new ItemData<Integer, BaseModel>(TYPE_PARTITION, forumItemDetailModel));
            }
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
            if (viewType == TYPE_VIDEO) {
                return new VideoVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
            }
            if (viewType == TYPE_PARTITION) {
                return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_detail_list_recelerview, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_VIDEO) {
                VideoVH vh = (VideoVH) holder;
                final VideoModel model = (VideoModel) recyclerViewData.get(position).getValue();

                vh.title.setText(model.getTitle());
                vh.nums.setText(model.getNums());
                vh.time.setText(model.getTime());
                PicassoUtils.normalShowImage(model.getImgSrc(), vh.imgPic);

                vh.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VideoActivity.newInstance(ForumLabelListActivity.this, model.getUrl());
                    }
                });
            }
            if (getItemViewType(position) == TYPE_PARTITION) {
                final ForumItemDetailModel model = (ForumItemDetailModel) recyclerViewData.get(position).getValue();
                VH vh = (VH) holder;
                vh.name.setText(model.getName());
                vh.theme.setText(String.format(getString(R.string.theme_count), model.getThemeCount()));
                vh.post.setText(String.format(getString(R.string.post_count), model.getPostCount()));
                vh.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ForumListActivity.newInstance(ForumLabelListActivity.this, model.getUrls());
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            return recyclerViewData.get(position).getKey();
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

        private class VideoVH extends RecyclerView.ViewHolder {

            private TextView nums;
            private TextView time;
            private TextView title;
            private ImageView imgPic;
            private CardView container;

            public VideoVH(View itemView) {
                super(itemView);
                container = (CardView) itemView.findViewById(R.id.container);
                nums = (TextView) itemView.findViewById(R.id.nums);
                time = (TextView) itemView.findViewById(R.id.time);
                title = (TextView) itemView.findViewById(R.id.videoTitleTextView);
                imgPic = (ImageView) itemView.findViewById(R.id.videoImageView);
            }
        }
    }
}
