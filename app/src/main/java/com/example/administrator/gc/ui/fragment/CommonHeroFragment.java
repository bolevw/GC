package com.example.administrator.gc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by liubo on 2016/5/30.
 */
public class CommonHeroFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_user_info, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public static CommonHeroFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CommonHeroFragment fragment = new CommonHeroFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView(View v) {

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
