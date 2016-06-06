package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.PictureListModel;
import com.example.administrator.gc.restApi.GankApi;
import com.example.administrator.gc.ui.fragment.childfragment.PictureCFragment;

/**
 * Created by liubo on 2016/6/3.
 */
public class PicturePresenter implements BasePresenter<PictureCFragment> {
    private PictureCFragment view;

    @Override
    public void bind(PictureCFragment view) {
        this.view = view;
    }

    public void getPicture(Integer index) {
        GankApi.getPicture(index, new BaseSub<PictureListModel, PictureCFragment>(view) {
            @Override
            protected void error(String e) {
                view.stopLoading();
            }

            @Override
            protected void next(PictureListModel pictureListModel) {
                view.stopLoading();
                view.notify(pictureListModel);
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
