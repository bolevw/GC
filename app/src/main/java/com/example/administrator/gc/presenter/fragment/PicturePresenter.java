package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.PictureListModel;
import com.example.administrator.gc.restApi.GankApi;
import com.example.administrator.gc.ui.fragment.childfragment.PictureCFragment;

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
        GankApi.getPicture(index, new Subscriber<PictureListModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.stopLoading();
                }
            }

            @Override
            public void onNext(PictureListModel model) {
                if (view != null) {
                    view.stopLoading();
                    view.notify(model);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
