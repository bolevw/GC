package com.boger.game.gc.presenter.fragment;

import android.util.Log;

import com.boger.game.gc.api.SquareApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.SquareListModel;
import com.boger.game.gc.ui.fragment.SquareFragment;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/5.
 */
public class SquarePresenter implements BasePresenter<SquareFragment> {

    private static final String TAG = "SquarePresenter";
    private SquareFragment view;

    @Override
    public void bind(SquareFragment view) {
        this.view = view;
    }

    public void getData() {
        SquareApi.getIndex(new Subscriber<List<SquareListModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.stopRefresh();
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(List<SquareListModel> list) {
                view.stopRefresh();
                view.notifyChange(list);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
