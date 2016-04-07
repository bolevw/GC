package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.api.ForumApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.ForumPostListItemModel;
import com.example.administrator.gc.ui.activity.ForumPostListActivity;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumPostListPresenter implements BasePresenter<ForumPostListActivity> {


    ForumPostListActivity view;

    @Override
    public void bind(ForumPostListActivity view) {
        this.view = view;
    }

    public void getData(String urls) {
        if (null != view) {
            view.startLoading();
        }
        ForumApi.getPost(urls, new Subscriber<ArrayList<ForumPostListItemModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (null != view) {
                    view.stopLoading();
                    view.logError(e);
                }
            }

            @Override
            public void onNext(ArrayList<ForumPostListItemModel> list) {
                if (null != view) {
                    view.stopLoading();
                    view.notifyChange(list);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
