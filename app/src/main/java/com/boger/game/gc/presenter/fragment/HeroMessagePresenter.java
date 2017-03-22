package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.HeroMessageModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.ui.fragment.HeroMessageFragment;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroMessagePresenter implements BasePresenter<HeroMessageFragment> {
    private HeroMessageFragment view;

    @Override
    public void bind(HeroMessageFragment view) {
        this.view = view;
    }

    public void getHeroMessage(String serverName, String playerName) {
        view.loading();
        LoLApi.getHeroMessage(serverName, playerName, new BaseSub<HeroMessageModel, HeroMessageFragment>(view) {
            @Override
            protected void error(String e) {
                view.loadingFail();
            }

            @Override
            protected void next(HeroMessageModel heroMessageModel) {
                view.stopLoading();
                if (null == heroMessageModel || heroMessageModel.getContent() == null || heroMessageModel.getContent().size() == 0) {
                    view.loadingFail();
                } else {
                    view.setResult(heroMessageModel);

                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
