package com.boger.game.gc.presenter.fragment;

import android.util.Log;

import com.boger.game.gc.api.SquareApi;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.SquareListModel;
import com.boger.game.gc.ui.fragment.SquareFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class SquarePresenter extends FragmentPresenter<SquareFragment> {

    private static final String TAG = "SquarePresenter";


    public SquarePresenter(SquareFragment view) {
        super(view);
    }

    public void getData() {
        SquareApi.getIndex(new ApiCallBack<List<SquareListModel>>(composite) {

            @Override
            protected void onSuccess(List<SquareListModel> list) {
                view.stopRefresh();
                view.notifyChange(list);
            }

            @Override
            protected void onFail(Throwable e) {
                view.stopRefresh();
                Log.e(TAG, "onError: " + e.toString());
            }

        });
    }
}