package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.api.AccountApi;
import com.example.administrator.gc.api.Urls;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.ui.fragment.MineFragment;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/29.
 */
public class MinePresenter implements BasePresenter<MineFragment> {

    private MineFragment view;

    @Override
    public void bind(MineFragment view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        this.view = null;
    }

    public void getData() {
        AccountApi.getLogin(Urls.INDEX_URL, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                view.show(s);
            }
        });
    }
}
