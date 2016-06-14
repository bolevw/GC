package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.api.IndexApi;
import com.example.administrator.gc.api.Urls;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.IndexModel;
import com.example.administrator.gc.ui.fragment.childfragment.RecommendCFragment;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecommendPresenter implements BasePresenter<RecommendCFragment> {
    private RecommendCFragment view;

    @Override
    public void bind(RecommendCFragment view) {
        this.view = view;
    }

    public void getData(final boolean swipe) {
        if (swipe)
            view.startLoading();
        IndexApi.getIndex(Urls.INDEX_URL, new Subscriber<IndexModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (null != view) {
                    view.logError(e);
                    view.stopRefresh();
                }
            }

            @Override
            public void onNext(IndexModel indexModel) {
                if (null != view) {
                    if (swipe) {
                        view.stopRefresh();
                    }
                    view.stopLoading();
                    view.notifyHotDataChange(indexModel);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
