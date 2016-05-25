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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liubo on 2016/5/24.
 */
public class SearchUserInfoFragment extends BaseFragment implements AreaListFragment.OnItemClickListener {

    @BindView(R.id.lolAreaButton)
    AppCompatButton areaButton;
    @BindView(R.id.resultTextView)
    TextView resultTextView;
    @BindView(R.id.userIdEditText)
    EditText userIdEditText;

    private AreaListFragment fragment = AreaListFragment.newInstance();
    private String area = "选择大区";

    @OnClick(R.id.lolAreaButton)
    void show() {
        getFragmentManager().beginTransaction().addToBackStack(AreaListFragment.class.getSimpleName()).add(R.id.fragmentContainer, fragment).commit();
//        FragmentUtils.replaceFragment(getFragmentManager(), R.id.fragmentContainer, fragment, true, AreaListFragment.class.getSimpleName());
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
        fragment.setOnItemClickListener(SearchUserInfoFragment.this);
    }

    @Override
    protected void unbind() {

    }

/*
    @Override
    public void onResume() {
        super.onResume();
        areaButton.setText(area);
    }*/

    @Override
    public void onItemClick(String area) {
        this.area = area;
        areaButton.setText(area);
    }
}
