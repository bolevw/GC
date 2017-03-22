package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.PlayerInfoModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.restApi.UserApi;
import com.boger.game.gc.ui.fragment.SearchUserInfoFragment;
import com.boger.game.gc.utils.ToastUtils;

import rx.Subscriber;

/**
 * Created by liubo on 2016/5/24.
 */
public class SearchUserInfoPresenter implements BasePresenter<SearchUserInfoFragment> {
    private SearchUserInfoFragment view;

    @Override
    public void bind(SearchUserInfoFragment view) {
        this.view = view;
    }

    public void search(String serverName, String playerName) {
        view.loading();
        LoLApi.getPlayerInfo(serverName, playerName, new BaseSub<PlayerInfoModel, SearchUserInfoFragment>(view) {
            @Override
            protected void error(String e) {
                view.loadingFail();
            }

            @Override
            protected void next(PlayerInfoModel playerInfoModel) {
                view.stopLoading();
                view.setResult(playerInfoModel);
            }
        });
    }


    public void saveAvatar(String avatarUrl, String id) {
        view.saveAva();
        UserApi.saveUserAvatar(avatarUrl, id, new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.saveAvaFail();
                ToastUtils.showNormalToast("保存失败！");
                view.logError(e);
            }

            @Override
            public void onNext(Void s) {
                view.saveAvaSuccess();
                ToastUtils.showNormalToast("保存成功！");
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
