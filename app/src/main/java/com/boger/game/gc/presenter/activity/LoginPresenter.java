package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.base.ActivityPresenter;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.UserModel;
import com.boger.game.gc.restApi.UserApi;
import com.boger.game.gc.ui.activity.LoginActivity;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginPresenter extends ActivityPresenter<LoginActivity> {
    private static final String TAG = "LoginPresenter";

    public LoginPresenter(LoginActivity view) {
        super(view);
    }


    public void login(final String username, String password) {
        UserApi.login(username, password, new ApiCallBack<UserModel>(composite) {
            @Override
            protected void onSuccess(UserModel data) {
                loginSuccess(data);

            }

            @Override
            protected void onFail(Throwable e) {
                view.showWarning(e.toString());
            }
        });
    }

    /**
     * 登录成功
     *
     * @param userModel
     */
    private void loginSuccess(UserModel userModel) {
        view.cache.saveStringValue(UserModel.SESSIONTOKEN, userModel.getSessionToken());
        view.cache.saveStringValue("username", userModel.getUsername());
        view.cache.saveStringValue("userId", userModel.getObjectId());
        view.cache.saveStringValue("avatar", userModel.getUserAvatarUrl());
        view.cache.saveBooleanValue("isLogin", true);
        view.loginSuccess();
    }
}
