package com.example.administrator.gc.ui.fragment.childfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;

/**
 * Created by Administrator on 2016/3/22.
 */
public class GameCFragment extends BaseFragment {

    private static final String TAG = "GameCFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.child_fragment_game, container, false);
        Log.d(TAG, "GameCFragment onCreateView");
        return v;
    }

    @Override
    protected void initView(View v) {

    }

    @Override
    protected void bind() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void unbind() {

    }
}
