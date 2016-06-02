package com.example.administrator.gc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.presenter.fragment.HeroMessagePresenter;

import butterknife.ButterKnife;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroMessageFragment extends BaseFragment {
    HeroMessagePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hreo_message, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initView(View v) {

    }

    @Override
    protected void bind() {
        presenter = new HeroMessagePresenter();
        presenter.bind(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void unbind() {
        presenter.unBind();
    }
}
