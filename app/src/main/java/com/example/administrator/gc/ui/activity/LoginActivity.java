package com.example.administrator.gc.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.presenter.activity.LoginPresenter;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginActivity extends BaseActivity {


    LoginPresenter presenter;

    EditText accountEditText;
    Button loginButton;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        accountEditText = (EditText) findViewById(R.id.accountEditText);

        loginButton = (Button) findViewById(R.id.loginButton);

    }

    @Override
    protected void setListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("url", accountEditText.getText().toString());
            }
        });
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
