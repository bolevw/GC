package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.CommonHeroModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.ui.fragment.CommonHeroFragment;

/**
 * Created by liubo on 2016/6/2.
 */
public class CommonHeroPresenter implements BasePresenter<CommonHeroFragment> {
    private CommonHeroFragment view;

    @Override
    public void bind(CommonHeroFragment view) {
        this.view = view;
    }

    public void getCommonHero(String serverName, String playerName) {
        view.loading();
        LoLApi.getCommonHero(serverName, playerName, new BaseSub<CommonHeroModel, CommonHeroFragment>(view) {
            @Override
            protected void error(String e) {
                view.loadingFail();
            }

            @Override
            protected void next(CommonHeroModel commonHeroModel) {
                view.stopLoading();
                view.setResult(commonHeroModel);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }


}
