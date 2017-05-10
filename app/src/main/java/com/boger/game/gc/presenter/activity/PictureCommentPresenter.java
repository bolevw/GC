package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.base.ActivityPresenter;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.CommentModel;
import com.boger.game.gc.model.GetCommentModel;
import com.boger.game.gc.restApi.CommentApi;
import com.boger.game.gc.ui.activity.PictureCommentActivity;

/**
 * Created by liubo on 16/8/2.
 */

public class PictureCommentPresenter extends ActivityPresenter<PictureCommentActivity> {


    public PictureCommentPresenter(PictureCommentActivity view) {
        super(view);
    }

    public void getComment(String url) {
        CommentApi.getComment(url, new ApiCallBack<GetCommentModel>(composite) {

            @Override
            protected void onSuccess(GetCommentModel list) {
                view.getDataSuccessful(list.getResults(), true);

            }

            @Override
            protected void onFail(Throwable e) {

            }
        });
    }

    public void comment(CommentModel model, final String url) {

        CommentApi.comment(model, new ApiCallBack<Void>(composite) {

            @Override
            protected void onSuccess(Void data) {
                view.commentSuccessful();
                getComment(url);
            }

            @Override
            protected void onFail(Throwable e) {

            }
        });
    }

    public void admirePic(String objectId, CommentModel model) {
        CommentApi.admirePic(objectId, model, new ApiCallBack<Void>(composite) {
            @Override
            protected void onSuccess(Void data) {

            }

            @Override
            protected void onFail(Throwable e) {

            }
        });
    }
}
