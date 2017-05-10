package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.api.ImageApi;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.ui.fragment.WebFragment;

/**
 * Created by liubo on 2017/5/3.
 */

public class WebPresenter extends FragmentPresenter<WebFragment> {

    public WebPresenter(WebFragment view) {
        super(view);
    }


    public void getData() {
        ImageApi.getImageCoverList("http://tu.duowan.com/scroll/133591.html", new ApiCallBack<Void>(composite) {

            @Override
            protected void onSuccess(Void data) {

            }

            @Override
            protected void onFail(Throwable e) {

            }
        });
    }
}
