package com.example.administrator.gc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.presenter.fragment.MinePresenter;
import com.example.administrator.gc.ui.activity.AboutActivity;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MineFragment extends BaseFragment {

    MinePresenter presenter;
    private TextView show;

    private LinearLayout aboutLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        return v;
    }

    @Override
    protected void initView(View v) {
        aboutLinearLayout = (LinearLayout) v.findViewById(R.id.aboutLinearLayout);
        show = (TextView) v.findViewById(R.id.show);
    }

    @Override
    protected void bind() {
        presenter = new MinePresenter();
        presenter.bind(this);
        presenter.getData();
    }

    public void show(String s) {
        show.setText(s);
    }

    @Override
    protected void setListener() {
        aboutLinearLayout.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == aboutLinearLayout.getId()) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        }
    };

    @Override
    protected void unbind() {
        presenter.unBind();
    }
}
