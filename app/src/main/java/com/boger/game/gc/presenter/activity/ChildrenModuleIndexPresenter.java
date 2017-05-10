package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.ForumApi;
import com.boger.game.gc.base.ActivityPresenter;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.ArticleCoverListModel;
import com.boger.game.gc.ui.activity.ChildrenModuleIndexActivity;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ChildrenModuleIndexPresenter extends ActivityPresenter<ChildrenModuleIndexActivity> {
    private String nextPageUrl = null;

    public ChildrenModuleIndexPresenter(ChildrenModuleIndexActivity view) {
        super(view);
    }

    public void getData(String urls) {
        view.startLoading();
        ForumApi.getPostList(urls, new ApiCallBack<ArticleCoverListModel>(composite) {

            @Override
            protected void onSuccess(ArticleCoverListModel model) {
                view.stopLoading();
                view.setLoading(false);
                view.notifyChange(model.getList());
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

    public void getMore() {
        getData(nextPageUrl);
    }
}
