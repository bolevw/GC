package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.RecordModel;
import com.example.administrator.gc.restApi.LoLApi;
import com.example.administrator.gc.ui.fragment.RecordFragment;

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
                view.loadingFail();
            }

            @Override
            protected void next(RecordModel recordModel) {
                view.stopLoading();
                view.setResult(recordModel);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
