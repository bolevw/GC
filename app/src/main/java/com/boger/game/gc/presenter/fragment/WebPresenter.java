package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.api.ImageApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.ui.fragment.WebFragment;

import rx.Subscriber;

/**
 * Created by liubo on 2017/5/3.
 */

public class WebPresenter implements BasePresenter<WebFragment> {
    WebFragment view;

    @Override
    public void bind(WebFragment view) {
        this.view = view;
    }

    public void getData() {
        ImageApi.getImageCoverList("http://tu.duowan.com/scroll/133591.html", new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {

            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
