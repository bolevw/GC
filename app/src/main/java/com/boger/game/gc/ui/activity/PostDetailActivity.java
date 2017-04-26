package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.api.Urls;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.base.ItemData;
import com.boger.game.gc.model.FollowPostModel;
import com.boger.game.gc.model.IsFollowModel;
import com.boger.game.gc.model.PostBodyModel;
import com.boger.game.gc.model.PostDetailHeaderModel;
import com.boger.game.gc.model.PostDetailModel;
import com.boger.game.gc.model.UserMessageModel;
import com.boger.game.gc.presenter.activity.PostDetailPresenter;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.boger.game.gc.utils.SnackbarUtils;
import com.boger.game.gc.widget.RecyclerViewCutLine;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.analytics.MobclickAgent;

import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PostDetailActivity extends BaseSwipeBackActivity {

    private static final String TAG = "PostDetailActivity";

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

    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, PostDetailActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void initViewData() {
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
            showLoginDialog();
        }
    }

    private void showLoginDialog() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forum, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, Urls.BASE_URL + urls);
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

    public void setFollow(boolean follow) {
        isFollow = follow;
        recyclerView.getAdapter().notifyItemChanged(0);
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ImageLoader imageLoader = new ImageLoader();

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
                bindHeader((HeaderVh) holder, position);
            } else if (getItemViewType(position) == TYPE_COMMENT) {
                bindComment((CommentVh) holder, position);
            } else if (getItemViewType(position) == TYPE_LOADING) {
                bindLoading((FootVh) holder);
            }
        }

        private void bindLoading(FootVh holder) {
            FootVh vh = holder;
            if (!presenter.isHasData()) {
                vh.loadingContent.setVisibility(View.GONE);
                vh.noDataTextView.setVisibility(View.VISIBLE);
            } else {
                vh.loadingContent.setVisibility(View.VISIBLE);
                vh.noDataTextView.setVisibility(View.GONE);
            }
        }

        private void bindComment(CommentVh holder, int position) {
            final ItemData<Integer, PostDetailModel> data = (ItemData<Integer, PostDetailModel>) viewData.get(position);
            final CommentVh vh = holder;
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
            supportCopyData(vh, content);
            ImageLoaderUtils.load(data.getValue().getUserMessageModel().getUserPhotoSrc(), vh.imageSrc);
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
        }

        private void supportCopyData(final CommentVh vh, String content) {
            imageLoader.container = vh.itemBodyContentTextView;
            vh.itemBodyContentTextView.setText(Html.fromHtml("<html><head></head><body>" + content + "</body></html>", imageLoader, null));
            vh.itemBodyContentTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) PostDetailActivity.this.getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("copy", vh.itemBodyContentTextView.getText().toString().trim());
                    clipboardManager.setPrimaryClip(clipData);

                    Observable.create(new Observable.OnSubscribe<Void>() {
                        @Override
                        public void call(Subscriber<? super Void> subscriber) {
                            subscriber.onNext(null);
                            subscriber.onError(null);
                            subscriber.onCompleted();
                        }
                    })
                            .subscribeOn(Schedulers.newThread())
                            .delay(50000, TimeUnit.MILLISECONDS)
                            .subscribe(new Subscriber<Void>() {
                                @Override
                                public void onCompleted() {
                                    ClipboardManager clipboardManager = (ClipboardManager) PostDetailActivity.this.getSystemService(CLIPBOARD_SERVICE);
                                    ClipData.Item paseData = clipboardManager.getPrimaryClip().getItemAt(0);
                                    String paseString = (String) paseData.getText();
                                    Log.d("pase", "pasteData: " + paseString);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(Void aVoid) {
                                    ClipboardManager clipboardManager = (ClipboardManager) PostDetailActivity.this.getSystemService(CLIPBOARD_SERVICE);
                                    ClipData.Item paseData = clipboardManager.getPrimaryClip().getItemAt(0);
                                    String paseString = (String) paseData.getText();
                                    Log.d("pase", "pasteData: " + paseString);
                                }
                            });
                    return true;
                }
            });
        }

        private void bindHeader(HeaderVh holder, int position) {
            final ItemData<Integer, PostDetailHeaderModel> data = (ItemData<Integer, PostDetailHeaderModel>) viewData.get(position);
            final UserMessageModel userMessageModel = data.getValue().getHeader();
            final HeaderVh vh = holder;

            vh.type.setText("楼主");
            vh.name.setText(userMessageModel.getUserName());
            ImageLoaderUtils.load(userMessageModel.getUserPhotoSrc(), vh.imageSrc);
            vh.imageSrc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> list = new ArrayList<String>();
                    list.add(userMessageModel.getUserPhotoSrc());
                    PhotoActivity.newInstance(PostDetailActivity.this, 0, list);
                }
            });

            vh.date.setText(userMessageModel.getDate());
            vh.itemPostTitleTextView.setText(data.getValue().getTitle());

            convertArticle(data, vh);

            supportCopyData(vh);

            handleFollowButton(data, vh);

            vh.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalHomePageActivity.newInstance(PostDetailActivity.this, data.getValue().getHeader().getUserHomePageUrl());
                }
            });
        }

        private void supportCopyData(final HeaderVh vh) {
            vh.itemBodyContentTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) PostDetailActivity.this.getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("copy", vh.itemBodyContentTextView.getText().toString().trim());
                    clipboardManager.setPrimaryClip(clipData);

                    Observable.create(new Observable.OnSubscribe<Void>() {
                        @Override
                        public void call(Subscriber<? super Void> subscriber) {
                            subscriber.onNext(null);
                            subscriber.onError(null);
                            subscriber.onCompleted();
                        }
                    })
                            .subscribeOn(Schedulers.io())
//                            .delay(2000, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Void>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(Void aVoid) {
                                    ClipboardManager clipboardManager = (ClipboardManager) PostDetailActivity.this.getSystemService(CLIPBOARD_SERVICE);
                                    ClipData.Item paseData = clipboardManager.getPrimaryClip().getItemAt(0);
                                    String paseString = (String) paseData.getText();
                                    Log.d("pase", "pasteData: " + paseString);
                                }
                            });
                    return true;
                }
            });
        }

        private void handleFollowButton(final ItemData<Integer, PostDetailHeaderModel> data, final HeaderVh vh) {
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
        }

        private void convertArticle(ItemData<Integer, PostDetailHeaderModel> data, HeaderVh vh) {
            String content = data.getValue().getContent();
            imageLoader.container = vh.itemBodyContentTextView;
            vh.itemBodyContentTextView.setText(Html.fromHtml("<html><head></head><body>" + content + "</body></html>", imageLoader, null));
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
            }
        }

        private class CommentVh extends RecyclerView.ViewHolder {
            private TextView name;
            private TextView date;
            private ImageView imageSrc;
            private TextView type;
            private TextView itemBodyContentTextView;
            private LinearLayout commentContent;

            public CommentVh(View itemView) {
                super(itemView);

                commentContent = (LinearLayout) itemView.findViewById(R.id.commentContent);
                name = (TextView) itemView.findViewById(R.id.itemUserNameTextView);
                date = (TextView) itemView.findViewById(R.id.itemPostDateTextView);
                imageSrc = (ImageView) itemView.findViewById(R.id.itemUserImageView);
                type = (TextView) itemView.findViewById(R.id.itemUserTypeTextView);
                itemBodyContentTextView = (TextView) itemView.findViewById(R.id.itemBodyContentTextView);
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

        private class ImageLoader implements Html.ImageGetter {
            protected TextView container;

            @Override
            public Drawable getDrawable(String source) {
                container.setMovementMethod(LinkMovementMethod.getInstance());
                final UrlDrawable drawable = new UrlDrawable();
                Glide.with(PostDetailActivity.this).load(source).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Drawable dr = resource;
                        if (dr instanceof BitmapDrawable) {
                            BitmapDrawable bd = (BitmapDrawable) dr;
                            drawable.bitmap = bd.getBitmap();
                        } else if (dr instanceof GlideBitmapDrawable) {
                            GlideBitmapDrawable gbd = (GlideBitmapDrawable) dr;
                            drawable.bitmap = gbd.getBitmap();
                        } else if (dr instanceof GifDrawable) {
                            GifDrawable gfd = (GifDrawable) dr;
                            drawable.bitmap = gfd.getFirstFrame();
                        }
                        drawable.setBounds(0, 0, resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
                        container.invalidate();
                        container.setText(container.getText());
                    }
                });

                return drawable;
            }
        }

        private class UrlDrawable extends BitmapDrawable {
            protected Bitmap bitmap;

            @Override
            public void draw(Canvas canvas) {
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, 0, 0, getPaint());
                }
            }
        }

        private class TagHandler implements Html.TagHandler {

            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                if (tag.toLowerCase().equals("img")) {

                }
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
