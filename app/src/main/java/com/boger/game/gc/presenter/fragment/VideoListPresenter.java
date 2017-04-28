package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.api.VideoApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.VideoIndexModel;
import com.boger.game.gc.ui.fragment.VideoListFragment;

import rx.Subscriber;

/**
 * Created by liubo on 2017/4/25.
 */

public class VideoListPresenter implements BasePresenter<VideoListFragment> {
    private VideoListFragment view;

    @Override
    public void bind(VideoListFragment view) {
        this.view = view;
    }

    public void getData(String url) {
        VideoApi.getVideoList(url, new Subscriber<VideoIndexModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(VideoIndexModel videoIndexModel) {
                view.getDataSuccess(videoIndexModel);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
