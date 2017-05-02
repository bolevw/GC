package com.boger.game.gc.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.presenter.fragment.MinePresenter;
import com.boger.game.gc.ui.activity.AboutActivity;
import com.boger.game.gc.ui.activity.LoginActivity;
import com.boger.game.gc.utils.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MineFragment extends BaseFragment {
    private MinePresenter presenter;
    @BindView(R.id.aboutLinearLayout)
    LinearLayout aboutLinearLayout;
    @BindView(R.id.loginButton)
    Button loginButton;
    private boolean isLogin;

    @BindView(R.id.avatarImageView)
    ImageView avatarImageView;
    @BindView(R.id.usernameTextView)
    TextView usernameTextView;

    @OnClick(R.id.loginButton)
    void login() {
        if (isLogin) {
            logOut();
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @OnClick(R.id.aboutLinearLayout)
    void about() {
        startActivity(new Intent(getActivity(), AboutActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViewData() {
        isLogin = cache.readBooleanValue("isLogin", false);

        if (isLogin) {
            loginButton.setText("退出登录");
            ImageLoaderUtils.load(cache.readStringValue("avatar", "default"), avatarImageView);
            usernameTextView.setText(cache.readStringValue("username", ""));
        } else {
            loginButton.setText("登录");
            usernameTextView.setText("");
        }
    }


    @Override
    protected void bind() {
        presenter = new MinePresenter();
        presenter.bind(this);

    }

    @Override
    protected void setListener() {

    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity())
                .setTitle("是否退出")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cache.saveBooleanValue("isLogin", false);
                        reset();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        reset();
    }

    private void reset() {
        isLogin = cache.readBooleanValue("isLogin", false);
        if (isLogin) {
            loginButton.setText("退出登录");
            ImageLoaderUtils.load(cache.readStringValue("avatar", "default"), avatarImageView);
            usernameTextView.setText(cache.readStringValue("username", ""));
        } else {
            avatarImageView.setImageResource(R.mipmap.ic_load_fail);
            loginButton.setText("登录");
            usernameTextView.setText("");
        }
    }

    @Override
    protected void unbind() {
        presenter.unBind();
    }
}
