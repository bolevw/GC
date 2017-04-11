package com.boger.game.gc.ui.fragment;

import android.os.Bundle;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;

/**
 * Created by liubo on 2016/5/30.
 */
public class ForecastFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forecast;
    }

    @Override
    protected void initViewData() {
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setTitle("段位预测");
        }
    }

    public static ForecastFragment newInstance() {

        Bundle args = new Bundle();

        ForecastFragment fragment = new ForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void bind() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void unbind() {

    }
}
