package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.api.AllGameApi;
import com.boger.game.gc.api.Urls;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.GameItemModel;
import com.boger.game.gc.ui.fragment.childfragment.GameCFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class GamePresenter extends FragmentPresenter<GameCFragment> {

    public GamePresenter(GameCFragment view) {
        super(view);
    }

    public void getData(final boolean swipe) {
        if (!swipe) {
            view.startLoading();
        }

        AllGameApi.getAllGame(Urls.INDEX_URL, new ApiCallBack<List<GameItemModel>>(composite) {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            protected void onSuccess(List<GameItemModel> data) {
                if (!swipe) {
                    view.stopLoading();
                } else {
                    view.stopRefresh();
                }
                view.notifyChange(data);
            }

            @Override
            protected void onFail(Throwable e) {
                view.logError(e);

                if (!swipe) {
                    view.stopLoading();
                } else {
                    view.stopRefresh();
                }
            }
        });
    }
}
