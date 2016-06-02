package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.ui.fragment.HeroMessageFragment;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroMessagePresenter implements BasePresenter<HeroMessageFragment> {
    HeroMessageFragment view;

    @Override
    public void bind(HeroMessageFragment view) {
        this.view = view;
    }

    public void getHeroMessage(String serverName, String playerName) {

    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
