package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseModel;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.base.ItemData;
import com.boger.game.gc.model.ChildrenModuleCoverModel;
import com.boger.game.gc.model.ForumIndexHeaderModel;
import com.boger.game.gc.model.ForumIndexModel;
import com.boger.game.gc.model.VideoModel;
import com.boger.game.gc.presenter.activity.ForumDetailListPresenter;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 论坛标签
 * Created by liubo on 2016/4/6.
 */
public class ForumIndexActivity extends BaseSwipeBackActivity {


    private static final int TYPE_VIDEO = 0x0001;
    private static final int TYPE_PARTITION = 0x0002;

    private ForumDetailListPresenter presenter;

    private String urls = "";

    @BindView(R.id.forumIndexRv)
    RecyclerView forumIndexRv;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.gameNameTv)
    TextView gameNameTv;
    @BindView(R.id.themeCountTv)
    TextView themeCountTv;
    @BindView(R.id.todayCountTv)
    TextView todayCountTv;
    @BindView(R.id.gameIconIv)
    ImageView gameIconIv;

    @OnClick(R.id.finishBtn)
    void finishBtn() {
        onBackPressed();
    }

    @OnClick(R.id.searchBtn)
    void searchBtn() {

    }

    @OnClick(R.id.attentionBtn)
    void attention() {

    }

    @OnClick(R.id.signInBtn)
    void signIn() {

    }

    ImageButton finishBtn, searchBtn;
    Button attentionBtn, signInBtn;


    private ArrayList<ItemData<Integer, BaseModel>> recyclerViewData = new ArrayList<>();

    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, ForumIndexActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forum_index;
    }

    @Override
    protected void initViewData() {
        Intent intent = getIntent();
        urls = intent.getStringExtra("urls");
        forumIndexRv = (RecyclerView) findViewById(R.id.forumIndexRv);
        forumIndexRv.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void setListener() {
        forumIndexRv.setAdapter(new RVAdapter());
    }

    public void notifyChange(ForumIndexModel model) {
        ForumIndexHeaderModel headerModel = model.getHeaderModel();
        String title = headerModel.getTitle();
        ImageLoaderUtils.load(headerModel.getIconUrls(), gameIconIv);
        gameNameTv.setText(title);
        titleTv.setText(title);
        themeCountTv.setText(String.format(getString(R.string.theme_count), headerModel.getThemeCounts()));
        todayCountTv.setText(String.format(getString(R.string.today_count), headerModel.getTodayCounts()));

        if (model.getList().size() == 0 && model.getVideoList().size() == 0) {
            ChildrenModuleIndexActivity.newInstance(ForumIndexActivity.this, urls);
            finish();
        } else {
            recyclerViewData.clear();
            for (VideoModel videoModel : model.getVideoList()) {
                recyclerViewData.add(new ItemData<Integer, BaseModel>(TYPE_VIDEO, videoModel));
            }
            for (ChildrenModuleCoverModel childrenModuleCoverModel : model.getList()) {
                recyclerViewData.add(new ItemData<Integer, BaseModel>(TYPE_PARTITION, childrenModuleCoverModel));
            }
            forumIndexRv.getAdapter().notifyDataSetChanged();
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
                ImageLoaderUtils.load(model.getImgSrc(), vh.imgPic);

                vh.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VideoActivity.newInstance(ForumIndexActivity.this, model.getUrl());
                    }
                });
            }
            if (getItemViewType(position) == TYPE_PARTITION) {
                final ChildrenModuleCoverModel model = (ChildrenModuleCoverModel) recyclerViewData.get(position).getValue();
                VH vh = (VH) holder;
                vh.name.setText(model.getName());
                vh.theme.setText(String.format(getString(R.string.theme_count), model.getThemeCount()));
                vh.post.setText(String.format(getString(R.string.post_count), model.getPostCount()));
                vh.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChildrenModuleIndexActivity.newInstance(ForumIndexActivity.this, model.getUrls());
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
