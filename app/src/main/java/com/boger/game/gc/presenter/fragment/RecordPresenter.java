package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.RecordModel;
import com.boger.game.gc.restApi.LoLApi;
import com.boger.game.gc.ui.fragment.RecordFragment;

/**
 * Created by liubo on 2016/5/30.
 */
public class RecordPresenter extends FragmentPresenter<RecordFragment> {

    public RecordPresenter(RecordFragment view) {
        super(view);
    }

    public void getRecord(String serverName, String username) {
        view.loading();
        LoLApi.getRecord(serverName, username, new ApiCallBack<RecordModel>(composite) {
            @Override
            protected void onSuccess(RecordModel recordModel) {
                view.stopLoading();
                if (null == recordModel) {
                    view.loadingFail();
                } else {
                    view.setResult(recordModel);
                }
            }

            @Override
            protected void onFail(Throwable e) {
                view.loadingFail();
            }
        });
    }
}
