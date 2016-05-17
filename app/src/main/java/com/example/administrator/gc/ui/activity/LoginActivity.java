package com.example.administrator.gc.ui.activity;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.presenter.activity.LoginPresenter;
import com.example.administrator.gc.utils.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginActivity extends BaseActivity {


    LoginPresenter presenter;

    EditText accountEditText;
    Button loginButton;

    private List<String> list = new ArrayList<>();

    @BindView(R.id.registerTextView)
    TextView register;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.findPasswordTextView)
    TextView findPasswordTextView;


    @BindView(R.id.rootView)
    CoordinatorLayout coordinatorLayout;

    @OnClick(R.id.registerTextView)
    void jump() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        accountEditText = (EditText) findViewById(R.id.accountEditText);

        loginButton = (Button) findViewById(R.id.loginButton);
    }

    @Override
    protected void setListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    public void showWarning(String string) {
        Snackbar sk = Snackbar.make(coordinatorLayout, string, Snackbar.LENGTH_SHORT);
        SnackbarUtils.setBackground(sk, this, android.R.color.holo_red_light);
        sk.show();
    }

    public void loginSuccess() {
        showWarning("登录成功");
        loginButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
    }

    private void login() {
        hideSoftKeyboard();
        String username = accountEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            showWarning("用户名、密码不能为空");
            return;
        }

        presenter.login(username, password);
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

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.item_video, parent, false);
            return view;
        }
    }
}
