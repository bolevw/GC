package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.api.ForumApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.ui.activity.PostDetailActivity;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PostDetailPresenter implements BasePresenter<PostDetailActivity> {

    PostDetailActivity view;

    @Override
    public void bind(PostDetailActivity view) {
        this.view = view;
    }

    public void getData(String urls) {
        ForumApi.getPostDetail(urls, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                if (null != view) {
                    view.stopLoading();
                    view.setShow(s);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
