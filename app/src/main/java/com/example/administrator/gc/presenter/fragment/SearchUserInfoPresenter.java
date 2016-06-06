package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.PlayerInfoModel;
import com.example.administrator.gc.restApi.LoLApi;
import com.example.administrator.gc.restApi.UserApi;
import com.example.administrator.gc.ui.fragment.SearchUserInfoFragment;
import com.example.administrator.gc.utils.ToastUtils;

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
