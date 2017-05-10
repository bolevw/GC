package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.PlayerInfoModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.restApi.UserApi;
import com.boger.game.gc.ui.fragment.SearchUserInfoFragment;
import com.boger.game.gc.utils.ToastUtils;

/**
 * Created by liubo on 2016/5/24.
 */
public class SearchUserInfoPresenter extends FragmentPresenter<SearchUserInfoFragment> {


    public SearchUserInfoPresenter(SearchUserInfoFragment view) {
        super(view);
    }

    public void search(String serverName, String playerName) {
        view.loading();
        LoLApi.getPlayerInfo(serverName, playerName, new ApiCallBack<PlayerInfoModel>(composite) {
            @Override
            protected void onSuccess(PlayerInfoModel data) {
                view.stopLoading();
                view.setResult(data);
            }

            @Override
            protected void onFail(Throwable e) {
                view.loadingFail();
            }

        });
    }


    public void saveAvatar(String avatarUrl, String id) {
        view.saveAva();
        UserApi.saveUserAvatar(avatarUrl, id, new ApiCallBack<Void>(composite) {
            @Override
            protected void onSuccess(Void data) {
                view.saveAvaSuccess();
                ToastUtils.showNormalToast("保存成功！");
            }

            @Override
            protected void onFail(Throwable e) {
                view.saveAvaFail();
                ToastUtils.showNormalToast("保存失败！");
                view.logError(e);
            }
        });
    }
}
