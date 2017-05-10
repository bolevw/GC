package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.api.VideoApi;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.VideoIndexModel;
import com.boger.game.gc.ui.fragment.VideoListFragment;

/**
 * Created by liubo on 2017/4/25.
 */

public class VideoListPresenter extends FragmentPresenter<VideoListFragment> {

    public VideoListPresenter(VideoListFragment view) {
        super(view);
    }

    public void getData(String url) {
        VideoApi.getVideoList(url, new ApiCallBack<VideoIndexModel>(composite) {

            @Override
            protected void onSuccess(VideoIndexModel data) {
                view.getDataSuccess(data);

            }

            @Override
            protected void onFail(Throwable e) {

            }

        });
    }
}
