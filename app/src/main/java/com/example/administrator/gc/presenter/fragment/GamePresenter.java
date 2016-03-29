package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.api.AllGameApi;
import com.example.administrator.gc.api.Urls;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.GameItemModel;
import com.example.administrator.gc.ui.fragment.childfragment.GameCFragment;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/29.
 */
public class GamePresenter implements BasePresenter<GameCFragment> {

    GameCFragment view;

    @Override
    public void bind(GameCFragment view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        this.view = null;
    }


    public void getData(final boolean swipe) {
        if (null != view) {
            if (!swipe) {
                view.startLoading();
            }
        }

        AllGameApi.getAllGame(Urls.INDEX_URL, new Subscriber<List<GameItemModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (null != view) {
                    if (!swipe) {
                        view.stopLoading();
                    } else {
                        view.stopRefresh();
                    }
                }
            }

            @Override
            public void onNext(List<GameItemModel> gameItemModels) {
                if (null != view) {
                    if (!swipe) {
                        view.stopLoading();
                    } else {
                        view.stopRefresh();
                    }

                    view.notifyChange(gameItemModels);

                }
            }

        });
    }
}
