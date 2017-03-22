package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.LevelModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.ui.fragment.LevelFragment;

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
