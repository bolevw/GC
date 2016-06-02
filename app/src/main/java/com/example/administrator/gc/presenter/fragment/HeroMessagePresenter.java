package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.HeroMessageModel;
import com.example.administrator.gc.restApi.LoLApi;
import com.example.administrator.gc.ui.fragment.HeroMessageFragment;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroMessagePresenter implements BasePresenter<HeroMessageFragment> {
    HeroMessageFragment view;

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
                view.setResult(heroMessageModel);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
