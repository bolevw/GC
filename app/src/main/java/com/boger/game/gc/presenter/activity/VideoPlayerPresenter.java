package com.boger.game.gc.presenter.activity;

import android.util.Log;

import com.boger.game.gc.api.VideoApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.VideoPlayerCoverModel;
import com.boger.game.gc.ui.activity.VideoPlayerActivity;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/27.
 */
public class VideoPlayerPresenter implements BasePresenter<VideoPlayerActivity> {

    private VideoPlayerActivity view;

    @Override
    public void bind(VideoPlayerActivity view) {
        this.view = view;
    }

    public void getData(String url) {
        VideoApi.getVideo(url, new Subscriber<VideoPlayerCoverModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(VideoPlayerCoverModel model) {
                Log.d("VideoPlayerCoverModel", model.getSrc());
            }
        });

    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
