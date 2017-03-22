package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.PictureListModel;
import com.boger.game.gc.restApi.GankApi;
import com.boger.game.gc.ui.fragment.childfragment.PictureCFragment;

import rx.Subscriber;

/**
 * Created by liubo on 2016/6/3.
 */
public class PicturePresenter implements BasePresenter<PictureCFragment> {
    private PictureCFragment view;

    @Override
    public void bind(PictureCFragment view) {
        this.view = view;
    }

    public void getPicture(final Integer index) {
        view.setLoading(true);
        GankApi.getPicture(index, new Subscriber<PictureListModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.stopLoading();
                    view.setLoading(false);
                }
            }

            @Override
            public void onNext(PictureListModel model) {
                if (view != null) {
                    view.stopLoading();
                    view.notify(model);
                    view.setLoading(false);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
