package com.example.administrator.gc.ui.activity;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.ui.fragment.SearchUserInfoFragment;
import com.example.administrator.gc.utils.FragmentUtils;

/**
 * Created by liubo on 2016/5/24.
 */
public class LoLActivity extends BaseActivity {


    @Override
    protected void initView() {
        setContentView(R.layout.activity_lol);
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, SearchUserInfoFragment.newInstance(), false, SearchUserInfoFragment.class.getSimpleName());
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void bind() {

    }

    @Override
    protected void unBind() {

    }
}
