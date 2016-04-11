package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.api.IndexApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.ForumModel;
import com.example.administrator.gc.ui.fragment.ForumFragment;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ForumPresenter implements BasePresenter<ForumFragment> {

    ForumFragment view;

    @Override
    public void bind(ForumFragment view) {
        this.view = view;
    }


    public void getData() {
        if (view != null) {
            view.startLoading();
        }
        IndexApi.getIndexDetail(new Subscriber<List<ForumModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.stopLoading();
                    view.logError(e);
                }
            }

            @Override
            public void onNext(List<ForumModel> forumModels) {
                if (null != view) {
                    view.stopLoading();
                    view.notifyChange(forumModels);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
