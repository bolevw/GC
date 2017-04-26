package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.base.BasePresenter;
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

    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
