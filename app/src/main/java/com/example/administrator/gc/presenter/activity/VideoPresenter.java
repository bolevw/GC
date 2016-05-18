package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.api.VideoApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.ui.activity.VideoActivity;

/**
 * Created by Administrator on 2016/4/27.
 */
public class VideoPresenter implements BasePresenter<VideoActivity> {

    VideoActivity view;

    @Override
    public void bind(VideoActivity view) {
        this.view = view;
    }

    public void getData(String url) {
        VideoApi.getVideo(url, new BaseSub<String, VideoActivity>(view) {
            @Override
            protected void error(String e) {

            }

            @Override
            protected void next(String s) {
                view.show(s);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
