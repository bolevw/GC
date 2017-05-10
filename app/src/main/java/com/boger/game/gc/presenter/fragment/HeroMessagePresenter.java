package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.HeroMessageModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.ui.fragment.HeroMessageFragment;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroMessagePresenter extends FragmentPresenter<HeroMessageFragment> {

    public HeroMessagePresenter(HeroMessageFragment view) {
        super(view);
    }

    public void getHeroMessage(String serverName, String playerName) {
        view.loading();
        LoLApi.getHeroMessage(serverName, playerName, new ApiCallBack<HeroMessageModel>(composite) {
            @Override
            protected void onSuccess(HeroMessageModel heroMessageModel) {
                view.stopLoading();
                if (null == heroMessageModel || heroMessageModel.getContent() == null || heroMessageModel.getContent().size() == 0) {
                    view.loadingFail();
                } else {
                    view.setResult(heroMessageModel);

                }
            }

            @Override
            protected void onFail(Throwable e) {
                view.loadingFail();
            }
        });
    }
}
