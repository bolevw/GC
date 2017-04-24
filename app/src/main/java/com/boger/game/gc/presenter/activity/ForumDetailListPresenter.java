package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.ForumApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.ArticleCoverListModel;
import com.boger.game.gc.model.ForumIndexModel;
import com.boger.game.gc.ui.activity.ForumIndexActivity;

import rx.Subscriber;

/**
 * Created by liubo on 2016/4/7.
 */
public class ForumDetailListPresenter implements BasePresenter<ForumIndexActivity> {

    private ForumIndexActivity view;

    private String nextPageUrl;

    @Override
    public void bind(ForumIndexActivity view) {
        this.view = view;
    }

    public void getData(String urls) {
        if (null != view) {
            view.startLoading();
        }
        ForumApi.getForumDetail(urls, new Subscriber<ForumIndexModel>() {
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
            public void onNext(ForumIndexModel model) {
                if (null != view) {
                    view.stopLoading();
                    view.notifyChange(model);
                    nextPageUrl = model.getArticleCoverListModel().getNextPageUrls();
                }
            }
        });
    }

    public void getMore() {
        getNextData(nextPageUrl);
    }

    public void getNextData(String urls) {
        if (null != view) {
            view.startLoading();
        }
        ForumApi.getPostList(urls, new Subscriber<ArticleCoverListModel>() {
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
            public void onNext(ArticleCoverListModel model) {
                if (null != view) {
                    view.stopLoading();
                    view.setLoading(false);
                    view.getNextPageSuc(model.getList());
                    nextPageUrl = model.getNextPageUrls();
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
