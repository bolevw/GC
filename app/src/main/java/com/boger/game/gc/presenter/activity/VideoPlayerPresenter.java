package com.boger.game.gc.presenter.activity;

import android.util.Log;

import com.boger.game.gc.api.VideoApi;
import com.boger.game.gc.base.ActivityPresenter;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.VideoChannelModel;
import com.boger.game.gc.model.VideoPlayerCoverModel;
import com.boger.game.gc.ui.activity.VideoPlayerActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class VideoPlayerPresenter extends ActivityPresenter<VideoPlayerActivity> {


    public VideoPlayerPresenter(VideoPlayerActivity view) {
        super(view);
    }


    public void getData(String url) {
        VideoApi.getVideo(url, new ApiCallBack<VideoPlayerCoverModel>(composite) {

            @Override
            protected void onSuccess(VideoPlayerCoverModel data) {
                view.getVideoStart(data);

            }

            @Override
            protected void onFail(Throwable e) {

            }
        });


        VideoApi.getRecVideoList(url, new ApiCallBack<List<VideoChannelModel>>(composite) {
            @Override
            protected void onSuccess(List<VideoChannelModel> data) {
                view.getList(data);

            }

            @Override
            protected void onFail(Throwable e) {
                Log.e("TAG", "onError: " + e.toString());

            }
        });
    }
}
