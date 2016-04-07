package com.example.administrator.gc.presenter.activity;

import android.util.Log;

import com.example.administrator.gc.api.ForumApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.ForumItemDetailModel;
import com.example.administrator.gc.ui.activity.ForumDetailListActivity;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumDetailListPresenter implements BasePresenter<ForumDetailListActivity> {

    ForumDetailListActivity view;

    @Override
    public void bind(ForumDetailListActivity view) {
        this.view = view;
    }

    public void getData(String urls) {

        ForumApi.getForum(urls, new Subscriber<ArrayList<ForumItemDetailModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("error", e.toString());

            }

            @Override
            public void onNext(ArrayList<ForumItemDetailModel> list) {
                view.nofityChange(list);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
