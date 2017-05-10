package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.ForumApi;
import com.boger.game.gc.base.ActivityPresenter;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.ArticleCoverListModel;
import com.boger.game.gc.model.ForumIndexModel;
import com.boger.game.gc.ui.activity.ForumIndexActivity;

/**
 * Created by liubo on 2016/4/7.
 */
public class ForumDetailListPresenter extends ActivityPresenter<ForumIndexActivity> {


    private String nextPageUrl;

    public ForumDetailListPresenter(ForumIndexActivity view) {
        super(view);
    }

    public void getData(String urls) {
        view.startLoading();
        ForumApi.getForumDetail(urls, new ApiCallBack<ForumIndexModel>(composite) {

            @Override
            protected void onSuccess(ForumIndexModel model) {
                view.stopLoading();
                view.notifyChange(model);
                nextPageUrl = model.getArticleCoverListModel().getNextPageUrls();
            }

            @Override
            protected void onFail(Throwable e) {
                view.logError(e);
                view.stopLoading();
            }
        });
    }

    public void getMore() {
        getNextData(nextPageUrl);
    }

    public void getNextData(String urls) {
        view.startLoading();
        ForumApi.getPostList(urls, new ApiCallBack<ArticleCoverListModel>(composite) {

            @Override
            protected void onSuccess(ArticleCoverListModel model) {
                view.stopLoading();
                view.setLoading(false);
                view.getNextPageSuc(model.getList());
                nextPageUrl = model.getNextPageUrls();
            }

            @Override
            protected void onFail(Throwable e) {
                view.stopLoading();
                view.logError(e);
                view.setLoading(false);
            }
        });
    }
}
