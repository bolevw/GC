package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.UserModel;
import com.example.administrator.gc.restApi.UserApi;
import com.example.administrator.gc.ui.activity.LoginActivity;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginPresenter implements BasePresenter<LoginActivity> {
    private static final String TAG = "LoginPresenter";
    private LoginActivity view;

    @Override
    public void bind(LoginActivity view) {
        this.view = view;
    }

    public void login(final String username, String password) {
        UserApi.login(username, password, new BaseSub<UserModel, LoginActivity>(view) {
            @Override
            protected void error(String e) {
                view.showWarning(e);
            }

            @Override
            protected void next(UserModel userModel) {
                view.cache.saveStringValue(UserModel.SESSIONTOKEN, userModel.getSessionToken());
                view.cache.saveStringValue("username", userModel.getUsername());
                view.cache.saveStringValue("userId", userModel.getObjectId());
                view.cache.saveBooleanValue("isLogin", true);
                view.loginSuccess();
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
