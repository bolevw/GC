package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.model.CommentModel;
import com.example.administrator.gc.model.GetCommentModel;
import com.example.administrator.gc.restApi.CommentApi;
import com.example.administrator.gc.ui.activity.PictureCommentActivity;

import rx.Subscriber;

/**
 * Created by liubo on 16/8/2.
 */

public class PictureCommentPresenter implements BasePresenter<PictureCommentActivity> {

    private PictureCommentActivity view;

    @Override
    public void bind(PictureCommentActivity view) {
        this.view = view;
    }


    public void getComment(String url) {
        CommentApi.getComment(url, new Subscriber<GetCommentModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetCommentModel list) {
                view.getDataSuccessful(list.getResults(), true);
            }
        });
    }

    public void comment(CommentModel model, final String url) {

        CommentApi.comment(model, new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                view.commentSuccessful();
                getComment(url);
            }
        });
    }

    public void admirePic(String objectId, CommentModel model) {
        CommentApi.admirePic(objectId, model, new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {

            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
