package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.LevelModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.ui.fragment.LevelFragment;

/**
 * Created by liubo on 2016/6/2.
 */
public class LevelPresenter extends FragmentPresenter<LevelFragment> {

    public LevelPresenter(LevelFragment view) {
        super(view);
    }


    public void getPlayerLevel(String serverName, String playerName) {
        view.loading();
        LoLApi.getPlayerLevel(serverName, playerName, new ApiCallBack<LevelModel>(composite) {
            @Override
            protected void onSuccess(LevelModel levelModel) {
                if (levelModel == null) {
                    view.loadingFail();
                } else {
                    view.stopLoading();
                    view.setResult(levelModel);
                }
            }

            @Override
            protected void onFail(Throwable e) {
                view.loadingFail();

            }
        });
    }
}
