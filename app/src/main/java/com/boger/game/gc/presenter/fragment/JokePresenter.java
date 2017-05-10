package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.JokeResponse;
import com.boger.game.gc.restApi.JokeApi;
import com.boger.game.gc.ui.fragment.childfragment.JokeCFragment;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokePresenter extends FragmentPresenter<JokeCFragment> {

    public JokePresenter(JokeCFragment view) {
        super(view);
    }

    public void getData(final int index) {
        view.setLoading(true);
        JokeApi.getJoke(index, new ApiCallBack<JokeResponse>(composite) {

            @Override
            protected void onSuccess(JokeResponse data) {
                view.setLoading(false);
                view.stopLoading();
                view.stopRefresh();
                view.setViewData(data, index);
            }

            @Override
            protected void onFail(Throwable e) {
                view.setLoading(false);
                view.stopLoading();
                view.stopRefresh();
            }
        });
    }
}
