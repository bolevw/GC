package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.LevelModel;
import com.example.administrator.gc.restApi.LoLApi;
import com.example.administrator.gc.ui.fragment.LevelFragment;

/**
 * Created by liubo on 2016/6/2.
 */
public class LevelPresenter implements BasePresenter<LevelFragment> {
    private LevelFragment view;

    @Override
    public void bind(LevelFragment view) {
        this.view = view;
    }

    public void getPlayerLevel(String serverName, String playerName) {
        view.loading();
        LoLApi.getPlayerLevel(serverName, playerName, new BaseSub<LevelModel, LevelFragment>(view) {
            @Override
            protected void error(String e) {
                view.loadingFail();
            }

            @Override
            protected void next(LevelModel levelModel) {
                if (levelModel == null) {
                    view.loadingFail();
                } else {
                    view.stopLoading();
                    view.setResult(levelModel);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
