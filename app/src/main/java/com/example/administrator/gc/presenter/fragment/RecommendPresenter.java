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

    RecommendCFragment view;

    @Override
    public void bind(RecommendCFragment view) {
        this.view = view;

    }

    public void getData() {
        IndexApi.getIndex(Urls.INDEX_URL, new Subscriber<IndexModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onNext(IndexModel indexModel) {
                if (null != view) {
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
