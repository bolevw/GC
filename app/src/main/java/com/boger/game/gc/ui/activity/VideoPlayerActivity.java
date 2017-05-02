package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.model.VideoChannelModel;
import com.boger.game.gc.model.VideoPlayerCoverModel;
import com.boger.game.gc.presenter.activity.VideoPlayerPresenter;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.boger.game.gc.utils.ToastUtils;
import com.boger.game.gc.widget.FixVideoView;
import com.boger.game.gc.widget.loadmoreRecyclerview.BaseAdapter;
import com.boger.game.gc.widget.loadmoreRecyclerview.LoadingMoreRv;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/27.
 */
public class VideoPlayerActivity extends BaseSwipeBackActivity {

    private VideoPlayerPresenter presenter;
    private String url;
    private List<VideoChannelModel> viewData = new ArrayList<>();

    @BindView(R.id.videoView)
    FixVideoView videoView;
    @BindView(R.id.videoRv)
    LoadingMoreRv loadingMoreRv;
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.playBtn)
    ImageButton imageButton;

    @OnClick(R.id.playBtn)
    void play() {
        cover.setVisibility(View.GONE);
        imageButton.setVisibility(View.GONE);
        videoView.start();
    }

    public static void newInstance(Activity activity, String url) {
        Intent intent = new Intent(activity, VideoPlayerActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initViewData() {

        Intent intent = getIntent();
        if (TextUtils.isEmpty(intent.getStringExtra("url"))) {
            ToastUtils.showNormalToast("视频地址错误！");
            finish();
        }
        url = intent.getStringExtra("url");


        videoView.setMediaController(new MediaController(this));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

        loadingMoreRv.setLayoutManager(new LinearLayoutManager(this));
        loadingMoreRv.setNestedScrollingEnabled(false);
    }

    @Override
    protected void setListener() {
        loadingMoreRv.setAdapter(new BaseAdapter() {
            @Override
            protected boolean loadWrong(boolean wrong) {
                return false;
            }

            @Override
            protected boolean hasMore(boolean more) {
                return false;
            }

            @Override
            protected void onBindItemVh(RecyclerView.ViewHolder holder, int position) {
                ItemVh vh = (ItemVh) holder;
                final VideoChannelModel model = viewData.get(vh.getAdapterPosition());
                vh.setText(R.id.titleTv, model.getName());
                vh.setText(R.id.numTv, model.getNum());
                vh.setText(R.id.userTv, model.getAuthor());
                vh.setImage(R.id.cover, model.getCover());
                vh.onClick(R.id.container, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VideoPlayerActivity.newInstance(VideoPlayerActivity.this, model.getHref());
                    }
                });
            }

            @Override
            protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
                return new ItemVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list, parent, false));
            }

            @Override
            protected int getItemType(int position) {
                return 0;
            }

            @Override
            protected String getEmptyText() {
                return "-- end --";
            }

            @Override
            protected int dataSize() {
                return viewData.size();
            }

            class ItemVh extends Vh {

                public ItemVh(View itemView) {
                    super(itemView);
                }
            }
        });

    }

    private String videoUrl;

    public void getVideoStart(VideoPlayerCoverModel model) {
        this.videoUrl = model.getSrc();
        ImageLoaderUtils.load(model.getPoster(), cover);
        videoView.setVideoURI(Uri.parse(videoUrl));
    }

    public void getList(List<VideoChannelModel> list) {
        this.viewData.clear();
        this.viewData.addAll(list);
        this.loadingMoreRv.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void bind() {
        this.presenter = new VideoPlayerPresenter();
        this.presenter.bind(this);
        this.presenter.getData(url);
    }

    @Override
    protected void unBind() {
        this.presenter.unBind();
    }
}
