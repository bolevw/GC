package com.example.administrator.gc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liubo on 2016/5/24.
 */
public class SearchUserInfoFragment extends BaseFragment {

    @BindView(R.id.lolAreaButton)
    AppCompatButton spinner;
    @BindView(R.id.resultTextView)
    TextView resultTextView;
    @BindView(R.id.userIdEditText)
    EditText userIdEditText;

    @OnClick(R.id.lolAreaButton)
    void show() {
        FragmentUtils.replaceFragment(getFragmentManager(), R.id.fragmentContainer, AreaListFragment.newInstance(), true, AreaListFragment.class.getSimpleName());
    }


    public static SearchUserInfoFragment newInstance() {

        Bundle args = new Bundle();

        SearchUserInfoFragment fragment = new SearchUserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_user_info, container, false);
        ButterKnife.bind(this, v);
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
