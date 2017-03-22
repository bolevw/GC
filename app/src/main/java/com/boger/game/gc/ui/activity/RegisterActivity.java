package com.boger.game.gc.ui.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseActivity;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.RegisterModel;
import com.boger.game.gc.model.UserModel;
import com.boger.game.gc.restApi.UserApi;
import com.boger.game.gc.utils.SnackbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/6.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.usernameEditText)
    TextInputEditText username;

    @BindView(R.id.passwordEditText)
    TextInputEditText password;

    @BindView(R.id.registerButton)
    AppCompatButton register;

    @BindView(R.id.rootView)
    CoordinatorLayout coordinatorLayout;

    @OnClick(R.id.registerButton)
    void onClick() {
        hideSoftKeyboard();
        String name = username.getText().toString();
        String pw = password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            showWarning("用户名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(pw)) {
            showWarning("密码不能为空!");
            return;
        }

        if (name.length() < 6) {
            showWarning("账号长度不能小于6位！");
            return;
        }

        if (pw.length() < 6) {
            showWarning("密码长度不能小于6位！");
            return;
        }

        RegisterModel model = new RegisterModel();
        model.setUsername(username.getText().toString());
        model.setPassword(password.getText().toString());
        UserApi.register(model, new BaseSub<UserModel, RegisterActivity>(this) {
            @Override
            protected void error(String e) {
                showWarning(e);
            }

            @Override
            protected void next(UserModel userModel) {
                Snackbar sk = Snackbar.make(coordinatorLayout, "注册成功！", Snackbar.LENGTH_SHORT);
                SnackbarUtils.setBackground(sk, RegisterActivity.this);
                sk.show();
                cache.saveStringValue("sessionToken", userModel.getSessionToken());
                cache.saveStringValue("username", userModel.getUsername());
                cache.saveStringValue("userId", userModel.getObjectId());
                username.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 400);
            }
        });
    }

    private void showWarning(String string) {
        Snackbar sk = Snackbar.make(coordinatorLayout, string, Snackbar.LENGTH_SHORT);
        SnackbarUtils.setBackground(sk, RegisterActivity.this, android.R.color.holo_red_light);
        sk.show();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

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
