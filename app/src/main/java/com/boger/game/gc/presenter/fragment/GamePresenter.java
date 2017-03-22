package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.api.AllGameApi;
import com.boger.game.gc.api.Urls;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.GameItemModel;
import com.boger.game.gc.ui.fragment.childfragment.GameCFragment;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/29.
 */
public class GamePresenter implements BasePresenter<GameCFragment> {
    private GameCFragment view;

    @Override
    public void bind(GameCFragment view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        this.view = null;
    }

    public void getData(final boolean swipe) {
        if (!swipe) {
            view.startLoading();
        }

        AllGameApi.getAllGame(Urls.INDEX_URL, new Subscriber<List<GameItemModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (null != view) {
                    view.logError(e);

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
