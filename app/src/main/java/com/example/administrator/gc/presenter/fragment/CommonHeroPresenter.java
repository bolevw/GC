package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.CommonHeroModel;
import com.example.administrator.gc.restApi.LoLApi;
import com.example.administrator.gc.ui.fragment.CommonHeroFragment;

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
