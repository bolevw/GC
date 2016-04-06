package com.example.administrator.gc.ui.activity;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.presenter.activity.LoginPresenter;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginActivity extends BaseActivity {


    LoginPresenter presenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void bind() {
        this.presenter = new LoginPresenter();
        this.presenter.bind(this);
        this.presenter.getData();
    }

    @Override
    protected void unBind() {
        this.presenter.unBind();
    }
}
