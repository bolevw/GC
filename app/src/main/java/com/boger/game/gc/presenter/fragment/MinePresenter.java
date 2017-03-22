package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.ui.fragment.MineFragment;

/**
 * Created by Administrator on 2016/3/29.
 */
public class MinePresenter implements BasePresenter<MineFragment> {

    private MineFragment view;

    @Override
    public void bind(MineFragment view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
