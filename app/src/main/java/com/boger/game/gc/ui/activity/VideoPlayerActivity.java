package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.VideoView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.presenter.activity.VideoPlayerPresenter;
import com.boger.game.gc.utils.ToastUtils;

/**
 * Created by Administrator on 2016/4/27.
 */
public class VideoPlayerActivity extends BaseSwipeBackActivity {

    private MediaPlayer player;
    private VideoPlayerPresenter presenter;
    private String url;
    private VideoView videoView;

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
        videoView = (VideoView) findViewById(R.id.videoView);
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.stopPlayback();
            }
        });
    }

    @Override
    protected void setListener() {

    }

    public void show(String s) {
/*        TextView text = (TextView) findViewById(R.id.show);
        text.setText(s);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());*/
     /*   Uri uri = Uri.parse(s);
        videoView.setVideoURI(uri);
        videoView.start();
*/
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
