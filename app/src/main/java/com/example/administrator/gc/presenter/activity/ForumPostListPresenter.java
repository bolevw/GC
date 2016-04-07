package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.api.ForumApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.ForumPostPageListItemModel;
import com.example.administrator.gc.ui.activity.ForumPostListActivity;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumPostListPresenter implements BasePresenter<ForumPostListActivity> {


    ForumPostListActivity view;

    private String nextPageUrl = null;

    @Override
    public void bind(ForumPostListActivity view) {
        this.view = view;
    }

    public void getData(String urls) {
        if (null != view) {
            view.startLoading();
        }
        ForumApi.getPost(urls, new Subscriber<ForumPostPageListItemModel>() {
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
            public void onNext(ForumPostPageListItemModel model) {
                if (null != view) {
                    view.stopLoading();
                    view.notifyChange(model.getList());
                    nextPageUrl = model.getNextPageUrls();
                }
            }
        });
    }


    public void getMore() {
        getData(nextPageUrl);
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
