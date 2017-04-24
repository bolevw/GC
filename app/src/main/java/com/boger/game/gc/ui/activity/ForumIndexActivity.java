package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.model.ArticleCoverModel;
import com.boger.game.gc.model.ChildrenModuleCoverModel;
import com.boger.game.gc.model.ForumIndexHeaderModel;
import com.boger.game.gc.model.ForumIndexModel;
import com.boger.game.gc.presenter.activity.ForumDetailListPresenter;
import com.boger.game.gc.ui.fragment.ChildModuleFragment;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 论坛标签
 * Created by liubo on 2016/4/6.
 */
public class ForumIndexActivity extends BaseSwipeBackActivity {

    private static final int TYPE_LOADING = 0x0001;
    private static final int TYPE_EMPTY = 0x0002;
    private static final int TYPE_NORMAL = 0x0003;

    private ForumDetailListPresenter presenter;
    private List<ChildrenModuleCoverModel> childrenModuleCoverModels = new ArrayList<>();

    private ChildModuleFragment childModuleFragment;

    private String urls = "";
    Button attentionBtn, signInBtn;

    private ArrayList<ArticleCoverModel> viewData = new ArrayList<>();
    private ArrayList<ChildrenModuleCoverModel> viewData2 = new ArrayList<>();

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
    @BindView(R.id.allForumBtn)
    Button allForumBtn;
    @BindView(R.id.videoBtn)
    Button videoBtn;
    @BindView(R.id.childModuleBtn)
    Button childModuleBtn;

    @OnClick(R.id.childModuleBtn)
    void childModule() {
        hideSoftKeyboard();
        getSupportFragmentManager().beginTransaction().addToBackStack("child").replace(R.id.container, childModuleFragment = ChildModuleFragment.newInstance(childrenModuleCoverModels), "child").commit();
        childModuleFragment.setOnItemClickListener(new ChildModuleFragment.OnItemClickListener() {
            @Override
            public void onItemClick(ChildrenModuleCoverModel moduleCoverModel) {
                ChildrenModuleIndexActivity.newInstance(ForumIndexActivity.this, moduleCoverModel.getName(), moduleCoverModel.getUrls());
            }
        });
    }


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

    private boolean isLoading = false;

    @Override
    protected void setListener() {
        forumIndexRv.addOnScrollListener(onScrollListener);
    }

    public void notifyChange(ForumIndexModel model) {
        ForumIndexHeaderModel headerModel = model.getHeaderModel();
        String title = headerModel.getTitle();
        ImageLoaderUtils.load(headerModel.getIconUrls(), gameIconIv);
        gameNameTv.setText(title);
        titleTv.setText(title);
        themeCountTv.setText(String.format(getString(R.string.theme_count), headerModel.getThemeCounts()));
        todayCountTv.setText(String.format(getString(R.string.today_count), headerModel.getTodayCounts()));


        if (!TextUtils.isEmpty(model.getVideoUrl())) {
            videoBtn.setVisibility(View.VISIBLE);
        }

        if (model.getArticleCoverListModel().getList().size() == 0) {
            childModuleBtn.setVisibility(View.GONE);
            viewData2.clear();
            viewData2.addAll(model.getList());
            forumIndexRv.setAdapter(new RVAdapter2());
        } else {
            if (model.getList().size() == 0) {
                childModuleBtn.setVisibility(View.GONE);
            } else {
                childModuleBtn.setVisibility(View.VISIBLE);
                childrenModuleCoverModels.clear();
                childrenModuleCoverModels.addAll(model.getList());
            }
            viewData.clear();
            viewData.addAll(model.getArticleCoverListModel().getList());
            forumIndexRv.setAdapter(new RVAdapter());
        }
        forumIndexRv.getAdapter().notifyDataSetChanged();
    }

    public void getNextPageSuc(ArrayList<ArticleCoverModel> list) {
        viewData.addAll(list);
        forumIndexRv.getAdapter().notifyDataSetChanged();
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager manager = forumIndexRv.getLayoutManager();
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
                final ArticleCoverModel model = viewData.get(position);

                VH vh = (VH) holder;
                vh.title.setText(model.getName());
                vh.auth.setText(model.getAuthName());
                vh.date.setText(model.getDate());
                vh.commentCount.setText(model.getCommentCount());
                vh.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PostDetailActivity.newInstance(ForumIndexActivity.this, model.getUrls());
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == viewData.size()) {
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

    private class RVAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_detail_list_recelerview, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            final ChildrenModuleCoverModel model = viewData2.get(position);
            VH vh = (VH) holder;
            vh.name.setText(model.getName());
            vh.theme.setText(String.format(getString(R.string.theme_count), model.getThemeCount()));
            vh.post.setText(String.format(getString(R.string.post_count), model.getPostCount()));
            vh.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChildrenModuleIndexActivity.newInstance(ForumIndexActivity.this, model.getName(), model.getUrls());
                }
            });

        }


        @Override
        public int getItemCount() {
            return viewData2.size();
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

    /*private class VideoVH extends RecyclerView.ViewHolder {

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
    }*/

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

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
