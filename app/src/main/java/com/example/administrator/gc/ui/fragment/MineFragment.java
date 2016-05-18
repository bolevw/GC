package com.example.administrator.gc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.presenter.fragment.MinePresenter;
import com.example.administrator.gc.ui.activity.AboutActivity;
import com.example.administrator.gc.ui.activity.LoginActivity;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MineFragment extends BaseFragment {
    private MinePresenter presenter;
    private LinearLayout aboutLinearLayout;
    private Button loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        return v;
    }

    @Override
    protected void initView(View v) {
        aboutLinearLayout = (LinearLayout) v.findViewById(R.id.aboutLinearLayout);
        loginButton = (Button) v.findViewById(R.id.loginButton);
    }

    @Override
    protected void bind() {
        presenter = new MinePresenter();
        presenter.bind(this);
        presenter.getData();
    }

    public void show(String s) {

    }

    @Override
    protected void setListener() {
        aboutLinearLayout.setOnClickListener(listener);
        loginButton.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == aboutLinearLayout.getId()) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
            if (id == loginButton.getId()) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }
    };

    @Override
    protected void unbind() {
        presenter.unBind();
    }
}
