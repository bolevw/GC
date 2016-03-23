package com.example.administrator.gc.presenter.fragment;

import android.util.Log;

import com.example.administrator.gc.api.IndexApi;
import com.example.administrator.gc.api.Urls;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.ui.fragment.childfragment.RecommendCFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecommendPresenter implements BasePresenter<RecommendCFragment> {

    RecommendCFragment view;

    @Override
    public void bind(RecommendCFragment view) {
        this.view = view;
        getHotList();
        getData();
    }

    private void getHotList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add("i" + i);
        }

        view.notifyHotDataChange(list);
    }


    public void getData() {
        IndexApi.getIndex(Urls.INDEX_URL, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                Log.d("liubo", s);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
