package com.boger.game.gc.ui.fragment.childfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecreationCFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.child_fragment_recommond, container, false);
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
