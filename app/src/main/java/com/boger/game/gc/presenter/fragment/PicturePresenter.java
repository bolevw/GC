package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.PictureListModel;
import com.boger.game.gc.restApi.GankApi;
import com.boger.game.gc.ui.fragment.childfragment.PictureCFragment;

/**
 * Created by liubo on 2016/6/3.
 */
public class PicturePresenter extends FragmentPresenter<PictureCFragment> {

    public PicturePresenter(PictureCFragment view) {
        super(view);
    }

    public void getPicture(final Integer index) {
        view.setLoading(true);
        GankApi.getPicture(index, new ApiCallBack<PictureListModel>(composite) {

            @Override
            protected void onSuccess(PictureListModel model) {
                view.stopLoading();
                view.notify(model);
                view.setLoading(false);
            }

            @Override
            protected void onFail(Throwable e) {
                view.stopLoading();
                view.setLoading(false);
            }
        });
    }
}
