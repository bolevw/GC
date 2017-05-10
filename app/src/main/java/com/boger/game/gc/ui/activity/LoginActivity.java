package com.boger.game.gc.ui.activity;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.presenter.activity.LoginPresenter;
import com.boger.game.gc.utils.SnackbarUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginActivity extends BaseSwipeBackActivity {
    private LoginPresenter presenter;

    private EditText accountEditText;
    private Button loginButton;

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
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewData() {
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
        this.presenter = new LoginPresenter(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }
}
