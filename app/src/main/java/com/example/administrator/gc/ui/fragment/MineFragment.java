package com.example.administrator.gc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.presenter.fragment.MinePresenter;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MineFragment extends BaseFragment {

    MinePresenter presenter;
    private TextView show;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        return v;
    }

    @Override
    protected void initView(View v) {show = (TextView) v.findViewById(R.id.show);
    }

    @Override
    protected void bind() {
        presenter = new MinePresenter();
        presenter.bind(this);
        presenter.getData();
        try {
            Intent intent  = new Intent(getActivity(), Class.forName("MainActivity.class"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show(String s) {
        show.setText(s);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void unbind() {
        presenter.unBind();
    }
}
