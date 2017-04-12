package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.api.IndexApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.model.ForumModel;
import com.boger.game.gc.ui.fragment.SquareFragment;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/5.
 */
public class SquarePresenter implements BasePresenter<SquareFragment> {

    private SquareFragment view;

    @Override
    public void bind(SquareFragment view) {
        this.view = view;
    }

    public void getData() {
        IndexApi.getIndexDetail(new Subscriber<List<ForumModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.logError(e);
                }
            }

            @Override
            public void onNext(List<ForumModel> forumModels) {
                if (null != view) {
                    view.notifyChange(forumModels);
                }
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
