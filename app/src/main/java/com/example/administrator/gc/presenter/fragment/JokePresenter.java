package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.JokeResponse;
import com.example.administrator.gc.restApi.JokeApi;
import com.example.administrator.gc.ui.fragment.childfragment.JokeCFragment;

import rx.Subscriber;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokePresenter implements BasePresenter<JokeCFragment> {
    private JokeCFragment view;

    @Override
    public void bind(JokeCFragment view) {
        this.view = view;
    }

    public void getData(final int index) {
        JokeApi.getJoke("1418816972", index, new Subscriber<JokeResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.stopLoading();
                    view.stopRefresh();
                }
            }

            @Override
            public void onNext(JokeResponse jokeResponse) {
                if (view != null) {
                    view.stopLoading();
                    view.stopRefresh();
                    view.setViewData(jokeResponse, index);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
