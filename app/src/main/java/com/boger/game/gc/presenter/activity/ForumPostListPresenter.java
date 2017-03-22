package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.ForumApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.ForumPostPageListItemModel;
import com.boger.game.gc.ui.activity.ForumListActivity;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumPostListPresenter implements BasePresenter<ForumListActivity> {
    private ForumListActivity view;
    private String nextPageUrl = null;

    @Override
    public void bind(ForumListActivity view) {
        this.view = view;
    }

    public void getData(String urls) {
        if (null != view) {
            view.startLoading();
        }
        ForumApi.getPostList(urls, new Subscriber<ForumPostPageListItemModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (null != view) {
                    view.stopLoading();
                    view.logError(e);
                    view.setLoading(false);
                }
            }

            @Override
            public void onNext(ForumPostPageListItemModel model) {
                if (null != view) {
                    view.stopLoading();
                    view.setLoading(false);
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
