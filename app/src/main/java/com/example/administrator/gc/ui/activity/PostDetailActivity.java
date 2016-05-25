package com.example.administrator.gc.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.api.Urls;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.base.ItemData;
import com.example.administrator.gc.model.FollowPostModel;
import com.example.administrator.gc.model.IsFollowModel;
import com.example.administrator.gc.model.PostBodyModel;
import com.example.administrator.gc.model.PostDetailHeaderModel;
import com.example.administrator.gc.model.PostDetailModel;
import com.example.administrator.gc.model.TransformContentModel;
import com.example.administrator.gc.presenter.activity.PostDetailPresenter;
import com.example.administrator.gc.utils.ConvertArticleUtils;
import com.example.administrator.gc.utils.PicassoUtils;
import com.example.administrator.gc.utils.SnackbarUtils;
import com.example.administrator.gc.widget.RecyclerViewCutLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PostDetailActivity extends BaseActivity {

    private static final int TYPE_HEADER = 1000;
    private static final int TYPE_COMMENT = 1001;
    private static final int TYPE_LOADING = 1002;

    private PostDetailPresenter presenter;

    private String urls;
    private boolean isLoading = false;
    private boolean isLogin;
    private String objectId;
    private Subscriber subscriber;
    private boolean isFollow = false;

    private ArrayList<ItemData> viewData = new ArrayList<>();

    @BindView(R.id.postDetailRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rootView)
    CoordinatorLayout coordinatorLayout;

    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, PostDetailActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);

        isLogin = cache.readBooleanValue("isLogin", false);
        Intent intent = getIntent();
        urls = intent.getStringExtra("urls");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RVAdapter());
    }

    @Override
    protected void setListener() {
        recyclerView.addItemDecoration(new RecyclerViewCutLine(getResources().getDimensionPixelSize(R.dimen.cut_line), 0));
        recyclerView.addOnScrollListener(onScrollListener);
    }

    /**
     * 显示snackBar
     *
     * @param string
     */
    public void showWarning(String string) {
        Snackbar sk = Snackbar.make(coordinatorLayout, string, Snackbar.LENGTH_SHORT);
        SnackbarUtils.setBackground(sk, this, android.R.color.holo_red_light);
        sk.show();
    }

    /**
     * 关注成功
     */
    public void followSuccess() {
        hideSoftKeyboard();
        showWarning("关注成功");
        Observable.just(null).doOnNext(new Action1<Object>() {
            @Override
            public void call(Object o) {

            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    protected void bind() {
        presenter = new PostDetailPresenter();
        presenter.bind(this);
        presenter.getData(urls, false);
        IsFollowModel model = new IsFollowModel();
        model.setPostUrl(urls);
        model.setUserId(cache.readStringValue("userId", "0"));
        presenter.isFollow(model);
    }

    @Override
    protected void unBind() {
        presenter.unBind();
    }

    public void notifyChange(PostBodyModel model, boolean getNextPage) {
        if (!getNextPage) {
            ItemData<Integer, PostDetailHeaderModel> headerData = new ItemData<>();
            headerData.setKey(TYPE_HEADER);
            headerData.setValue(model.getHeader());
            viewData.add(headerData);
        }

        for (PostDetailModel detailModel : model.getCommentList()) {
            ItemData<Integer, PostDetailModel> detail = new ItemData<>();
            detail.setKey(TYPE_COMMENT);
            detail.setValue(detailModel);
            viewData.add(detail);
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void notifyNoData() {
        recyclerView.getAdapter().notifyItemChanged(viewData.size());
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                return new HeaderVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_detail_header_recyclerview, parent, false));
            } else if (viewType == TYPE_LOADING) {
                return new FootVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false));
            } else {
                return new CommentVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_detail_recyclerview, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (getItemViewType(position) == TYPE_HEADER) {
                final ItemData<Integer, PostDetailHeaderModel> data = (ItemData<Integer, PostDetailHeaderModel>) viewData.get(position);
                final HeaderVh vh = (HeaderVh) holder;
                vh.type.setText("楼主");
                vh.name.setText(data.getValue().getHeader().getUserName());
                PicassoUtils.normalShowImage(PostDetailActivity.this, data.getValue().getHeader().getUserPhotoSrc(), vh.imageSrc);
                vh.imageSrc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> list = new ArrayList<String>();
                        list.add(data.getValue().getHeader().getUserPhotoSrc());

                        PhotoActivity.newInstance(PostDetailActivity.this, 0, list);
                    }
                });
                vh.date.setText(data.getValue().getHeader().getDate());
                vh.itemPostTitleTextView.setText(data.getValue().getTitle());
                String content = data.getValue().getContent();
                TransformContentModel contentModel = ConvertArticleUtils.convert(content);
                if (contentModel.getPicUrls().size() > 0) {
                    content = contentModel.getArticle();
                    vh.setUrls(contentModel.getPicUrls());
                } else {
                    vh.picRecyclerView.setVisibility(View.GONE);
                }
                vh.itemBodyContentTextView.setText(content);
                vh.itemFollowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isFollow) {
                            followPost(data.getValue());
                        } else {
                            presenter.cancelFollow(getObjectId());
                        }
                    }
                });
                subscriber = new Subscriber() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Object o) {
                        isFollow = true;
                        vh.itemFollowButton.setText("取消关注");

                    }
                };
                if (isFollow) {
                    vh.itemFollowButton.setText("取消关注");
                } else {
                    vh.itemFollowButton.setText("关注");
                }
                vh.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersonalHomePageActivity.newInstance(PostDetailActivity.this, data.getValue().getHeader().getUserHomePageUrl());
                    }
                });
            } else if (getItemViewType(position) == TYPE_COMMENT) {
                final ItemData<Integer, PostDetailModel> data = (ItemData<Integer, PostDetailModel>) viewData.get(position);
                final CommentVh vh = (CommentVh) holder;
                vh.date.setText(data.getValue().getUserMessageModel().getDate());
                vh.name.setText(data.getValue().getUserMessageModel().getUserName());
                if (position == 1) {
                    vh.type.setText("沙发");
                } else if (position == 2) {
                    vh.type.setText("板凳");
                } else if (position == 3) {
                    vh.type.setText("地板");
                } else {
                    vh.type.setText(position + "楼");
                }

                String content = data.getValue().getContent();
                TransformContentModel contentModel = ConvertArticleUtils.convert(content);
                if (contentModel.getPicUrls().size() > 0) {
                    content = contentModel.getArticle();
                    vh.setUrls(contentModel.getPicUrls());
                } else {
                    vh.picRecyclerView.setVisibility(View.GONE);
                }
                vh.itemBodyContentTextView.setText(content);
                PicassoUtils.normalShowImage(PostDetailActivity.this, data.getValue().getUserMessageModel().getUserPhotoSrc(), vh.imageSrc);
                vh.imageSrc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> list = new ArrayList<String>();
                        list.add(data.getValue().getUserMessageModel().getUserPhotoSrc());
                        PhotoActivity.newInstance(PostDetailActivity.this, 0, list);
                    }
                });
                vh.commentContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersonalHomePageActivity.newInstance(PostDetailActivity.this, data.getValue().getUserMessageModel().getUserHomePageUrl());
                    }
                });

            } else if (getItemViewType(position) == TYPE_LOADING) {
                FootVh vh = (FootVh) holder;
                if (!presenter.isHasData()) {
                    vh.loadingContent.setVisibility(View.GONE);
                    vh.noDataTextView.setVisibility(View.VISIBLE);
                } else {
                    vh.loadingContent.setVisibility(View.VISIBLE);
                    vh.noDataTextView.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 && viewData.size() != 0) {
                return TYPE_HEADER;
            } else if (position == viewData.size()) {
                return TYPE_LOADING;
            } else {
                return TYPE_COMMENT;
            }
        }

        @Override
        public int getItemCount() {
            return viewData.size() == 0 ? 0 : viewData.size() + 1;
        }

        private class HeaderVh extends RecyclerView.ViewHolder {
            private TextView name;
            private TextView date;
            private ImageView imageSrc;
            private TextView type;
            private TextView itemPostTitleTextView;
            private TextView itemBodyContentTextView;
            private LinearLayout content;
            private Button itemFollowButton;
            private RecyclerView picRecyclerView;
            private List<String> urls = new ArrayList<>();

            public HeaderVh(View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.itemUserNameTextView);
                date = (TextView) itemView.findViewById(R.id.itemPostDateTextView);
                imageSrc = (ImageView) itemView.findViewById(R.id.itemUserImageView);
                type = (TextView) itemView.findViewById(R.id.itemUserTypeTextView);
                itemPostTitleTextView = (TextView) itemView.findViewById(R.id.itemPostTitleTextView);
                itemBodyContentTextView = (TextView) itemView.findViewById(R.id.itemBodyContentTextView);
                itemFollowButton = (Button) itemView.findViewById(R.id.itemFollowButton);
                content = (LinearLayout) itemView.findViewById(R.id.commentContent);
                picRecyclerView = (RecyclerView) itemView.findViewById(R.id.picRecyclerView);

                picRecyclerView.setLayoutManager(new LinearLayoutManager(PostDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                picRecyclerView.setAdapter(new PicAdapter());
            }

            private class PicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    HeaderVH vh = new HeaderVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false));
                    return vh;
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    HeaderVH vh = (HeaderVH) holder;
                    vh.text.setText("图" + position);
                    PicassoUtils.normalShowImage(PostDetailActivity.this, urls.get(position), vh.pic);
                    vh.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PhotoActivity.newInstance(PostDetailActivity.this, position, urls);
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return urls.size();
                }

                private class HeaderVH extends RecyclerView.ViewHolder {
                    private TextView text;
                    private ImageView pic;

                    public HeaderVH(View itemView) {
                        super(itemView);
                        text = (TextView) itemView.findViewById(R.id.tagTextView);
                        pic = (ImageView) itemView.findViewById(R.id.picImageView);
                    }
                }
            }

            public List<String> getUrls() {
                return urls;
            }

            public void setUrls(List<String> urls) {
                this.urls = urls;
                this.picRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        private class CommentVh extends RecyclerView.ViewHolder {
            private TextView name;
            private TextView date;
            private ImageView imageSrc;
            private TextView type;
            private TextView itemBodyContentTextView;
            private LinearLayout commentContent;
            private RecyclerView picRecyclerView;
            private List<String> urls = new ArrayList<>();

            public CommentVh(View itemView) {
                super(itemView);

                commentContent = (LinearLayout) itemView.findViewById(R.id.commentContent);
                name = (TextView) itemView.findViewById(R.id.itemUserNameTextView);
                date = (TextView) itemView.findViewById(R.id.itemPostDateTextView);
                imageSrc = (ImageView) itemView.findViewById(R.id.itemUserImageView);
                type = (TextView) itemView.findViewById(R.id.itemUserTypeTextView);
                itemBodyContentTextView = (TextView) itemView.findViewById(R.id.itemBodyContentTextView);
                picRecyclerView = (RecyclerView) itemView.findViewById(R.id.picRecyclerView);

                picRecyclerView.setLayoutManager(new LinearLayoutManager(PostDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                picRecyclerView.setAdapter(new PicAdapter());
            }

            private class PicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new CommentVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false));
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    CommentVH vh = (CommentVH) holder;
                    vh.text.setText("图" + position);
                    PicassoUtils.normalShowImage(PostDetailActivity.this, urls.get(position), vh.pic);
                    vh.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PhotoActivity.newInstance(PostDetailActivity.this, position, urls);
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return urls.size();
                }

                private class CommentVH extends RecyclerView.ViewHolder {
                    private TextView text;
                    private ImageView pic;

                    public CommentVH(View itemView) {
                        super(itemView);
                        text = (TextView) itemView.findViewById(R.id.tagTextView);
                        pic = (ImageView) itemView.findViewById(R.id.picImageView);
                    }
                }
            }

            public List<String> getUrls() {
                return urls;
            }

            public void setUrls(List<String> urls) {
                this.urls = urls;
                this.picRecyclerView.getAdapter().notifyDataSetChanged();
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

    private void followPost(PostDetailHeaderModel i) {
        if (isLogin) {
            FollowPostModel model = new FollowPostModel();
            model.setFollowDate(System.currentTimeMillis());
            model.setLzName(i.getHeader().getUserName());
            model.setPostUrl(urls);
            model.setUsername(cache.readStringValue("username", "default_name"));
            model.setUserId(cache.readStringValue("userId", "000"));
            model.setPostTitle(i.getTitle());
            presenter.followPost(model);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final AlertDialog adl = builder.create();
            builder.setTitle("请登录！")
                    .setMessage("请先登录，在进行关注！")
                    .setNegativeButton("暂不关注", null)
                    .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adl.dismiss();
                            startActivity(new Intent(PostDetailActivity.this, LoginActivity.class));
                        }
                    });
            builder.show();
        }
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        int position;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            position = manager.findLastVisibleItemPosition();
            if (position == viewData.size() && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                presenter.getNextPage();
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forum, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, Urls.BASE_URL + "/" + urls);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("text/plain"); // 纯文本
            intent.putExtra(Intent.EXTRA_SUBJECT, "ss");
            startActivity(Intent.createChooser(intent, "分享"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }


    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
        recyclerView.getAdapter().notifyItemChanged(0);
    }


}
