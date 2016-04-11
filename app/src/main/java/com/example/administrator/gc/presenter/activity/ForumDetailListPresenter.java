package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.api.ForumApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.ForumItemDetailModel;
import com.example.administrator.gc.ui.activity.ForumDetailListActivity;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumDetailListPresenter implements BasePresenter<ForumDetailListActivity> {

    ForumDetailListActivity view;

    @Override
    public void bind(ForumDetailListActivity view) {
        this.view = view;
    }

    public void getData(String urls) {
        if (null != view) {
            view.startLoading();
        }
        ForumApi.getForum(urls, new Subscriber<ArrayList<ForumItemDetailModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (null != view) {
                    view.logError(e);
                    view.stopLoading();
                }

            }

            @Override
            public void onNext(ArrayList<ForumItemDetailModel> list) {
                if (null != view) {
                    view.stopLoading();
                    view.nofityChange(list);

                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
