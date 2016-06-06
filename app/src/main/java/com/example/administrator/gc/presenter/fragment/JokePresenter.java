package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.JokeResponse;
import com.example.administrator.gc.restApi.JokeApi;
import com.example.administrator.gc.ui.fragment.childfragment.JokeCFragment;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokePresenter implements BasePresenter<JokeCFragment> {
    private JokeCFragment view;

    @Override
    public void bind(JokeCFragment view) {
        this.view = view;
    }

    public void getData(int index) {
        JokeApi.getJoke("1418816972", index, new BaseSub<JokeResponse, JokeCFragment>(view) {
            @Override
            protected void error(String e) {
                view.stopLoading();
                view.stopRefresh();
            }

            @Override
            protected void next(JokeResponse jokeResponse) {
                view.stopLoading();
                view.stopRefresh();
                view.setViewData(jokeResponse);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
