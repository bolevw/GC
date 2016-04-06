package com.example.administrator.gc.presenter.activity;

import android.util.Log;

import com.example.administrator.gc.api.AccountApi;
import com.example.administrator.gc.api.Urls;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.ui.activity.LoginActivity;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginPresenter implements BasePresenter<LoginActivity> {

    private static final String TAG = "LoginPresenter";
    LoginActivity view;

    @Override
    public void bind(LoginActivity view) {
        this.view = view;
    }

    public void getData() {
        AccountApi.getLogin(Urls.LOGIN, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);

            }
        });

    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
