package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.ui.fragment.SearchUserInfoFragment;

/**
 * Created by liubo on 2016/5/24.
 */
public class SearchUserInfoPresenter implements BasePresenter<SearchUserInfoFragment> {
    SearchUserInfoFragment view;

    @Override
    public void bind(SearchUserInfoFragment view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
