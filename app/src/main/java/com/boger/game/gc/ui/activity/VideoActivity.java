package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.VideoView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseActivity;
import com.boger.game.gc.presenter.activity.VideoPresenter;

/**
 * Created by Administrator on 2016/4/27.
 */
public class VideoActivity extends BaseActivity {

    private MediaPlayer player;
    private VideoPresenter presenter;
    private String url;
    private VideoView videoView;

    public static void newInstance(Activity activity, String url) {
        Intent intent = new Intent(activity, VideoActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_video);

     /*   Intent intent = getIntent();
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
        });*/
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
  /*      this.presenter = new VideoPresenter();
        this.presenter.bind(this);
        this.presenter.getData(url);
  */  }

    @Override
    protected void unBind() {
//        this.presenter.unBind();
    }
}
