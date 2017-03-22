package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.VideoApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.ui.activity.VideoActivity;

/**
 * Created by Administrator on 2016/4/27.
 */
public class VideoPresenter implements BasePresenter<VideoActivity> {

    private VideoActivity view;

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
