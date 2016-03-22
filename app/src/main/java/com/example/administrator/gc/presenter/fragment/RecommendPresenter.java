package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.ui.fragment.childfragment.RecommendCFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecommendPresenter implements BasePresenter<RecommendCFragment> {

    RecommendCFragment view;

    @Override
    public void bind(RecommendCFragment view) {
        this.view = view;
        getHotList();
    }

    private void getHotList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add("i" + i);
        }

        view.notifyHotDataChange(list);
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
