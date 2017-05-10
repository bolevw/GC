package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.api.IndexApi;
import com.boger.game.gc.api.Urls;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.IndexModel;
import com.boger.game.gc.ui.fragment.childfragment.RecommendCFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecommendPresenter extends FragmentPresenter<RecommendCFragment> {

    public RecommendPresenter(RecommendCFragment view) {
        super(view);
    }

    public void getData(final boolean swipe) {
        if (swipe)
            view.startLoading();
        IndexApi.getIndex(Urls.INDEX_URL, new ApiCallBack<IndexModel>(composite) {
            @Override
            protected void onSuccess(IndexModel data) {
                if (swipe) {
                    view.stopRefresh();
                }
                view.stopLoading();
                view.notifyHotDataChange(data);
            }

            @Override
            protected void onFail(Throwable e) {
                view.logError(e);
                view.stopRefresh();
            }
        });
    }

    public void startAutoScroll() {
        Observable.interval(2, 4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallBack<Long>(composite) {
                    @Override
                    protected void onSuccess(Long data) {

                    }

                    @Override
                    protected void onFail(Throwable e) {

                    }
                });
    }
}
