package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.api.ForumApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.ForumPartitionModel;
import com.example.administrator.gc.ui.activity.ForumLabelListActivity;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumDetailListPresenter implements BasePresenter<ForumLabelListActivity> {

    ForumLabelListActivity view;

    @Override
    public void bind(ForumLabelListActivity view) {
        this.view = view;
    }

    public void getData(String urls) {
        if (null != view) {
            view.startLoading();
        }
        ForumApi.getForum(urls, new Subscriber<ForumPartitionModel>() {
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
            public void onNext(ForumPartitionModel model) {
                if (null != view) {
                    view.stopLoading();
                    view.notifyChange(model);

                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
