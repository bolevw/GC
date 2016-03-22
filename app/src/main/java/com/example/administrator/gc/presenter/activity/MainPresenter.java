package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.ui.activity.MainActivity;

/**
 * Created by Administrator on 2016/3/21.
 */
public class MainPresenter implements BasePresenter<MainActivity> {

    MainActivity view;


    @Override
    public void bind(MainActivity view) {
        this.view = view;
    }

    @Override
    public void unBind() {

    }
}
