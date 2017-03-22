package com.boger.game.gc.presenter.fragment;

import android.util.Log;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.RecordModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.ui.fragment.RecordFragment;

/**
 * Created by liubo on 2016/5/30.
 */
public class RecordPresenter implements BasePresenter<RecordFragment> {
    private RecordFragment view;

    @Override
    public void bind(RecordFragment view) {
        this.view = view;
    }

    public void getRecord(String serverName, String username) {
        view.loading();
        LoLApi.getRecord(serverName, username, new BaseSub<RecordModel, RecordFragment>(view) {
            @Override
            protected void error(String e) {
                Log.e("error", e);
                view.loadingFail();
            }

            @Override
            protected void next(RecordModel recordModel) {
                view.stopLoading();
                if (null == recordModel) {
                    view.loadingFail();
                } else {
                    view.setResult(recordModel);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
