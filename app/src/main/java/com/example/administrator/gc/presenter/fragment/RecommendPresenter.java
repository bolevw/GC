package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.api.IndexApi;
import com.example.administrator.gc.api.Urls;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.IndexModel;
import com.example.administrator.gc.ui.fragment.childfragment.RecommendCFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public void startAutoScroll() {
        Observable.interval(2, 4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }
                });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
