package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.ForumApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.ArticleCoverListModel;
import com.boger.game.gc.ui.activity.ChildrenModuleIndexActivity;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ChildrenModuleIndexPresenter implements BasePresenter<ChildrenModuleIndexActivity> {
    private ChildrenModuleIndexActivity view;
    private String nextPageUrl = null;

    @Override
    public void bind(ChildrenModuleIndexActivity view) {
        this.view = view;
    }

    public void getData(String urls) {
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
