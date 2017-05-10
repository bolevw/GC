package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.CommonHeroModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.ui.fragment.CommonHeroFragment;

/**
 * Created by liubo on 2016/6/2.
 */
public class CommonHeroPresenter extends FragmentPresenter<CommonHeroFragment> {

    public CommonHeroPresenter(CommonHeroFragment view) {
        super(view);
    }

    public void getCommonHero(String serverName, String playerName) {
        view.loading();
        LoLApi.getCommonHero(serverName, playerName, new ApiCallBack<CommonHeroModel>(composite) {
            @Override
            protected void onSuccess(CommonHeroModel data) {
                view.stopLoading();
                view.setResult(data);
            }

            @Override
            protected void onFail(Throwable e) {
                view.loadingFail();
            }
        });
    }
}
