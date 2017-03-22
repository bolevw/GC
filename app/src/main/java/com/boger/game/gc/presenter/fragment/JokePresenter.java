package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.JokeResponse;
import com.boger.game.gc.restApi.JokeApi;
import com.boger.game.gc.ui.fragment.childfragment.JokeCFragment;

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
        view.setLoading(true);
        JokeApi.getJoke(index, new Subscriber<JokeResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.setLoading(false);
                    view.stopLoading();
                    view.stopRefresh();
                }
            }

            @Override
            public void onNext(JokeResponse jokeResponse) {
                if (view != null) {
                    view.setLoading(false);
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
