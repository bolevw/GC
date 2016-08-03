package com.example.administrator.gc.restApi;

import com.example.administrator.gc.model.CommentModel;
import com.example.administrator.gc.model.GetCommentModel;
import com.example.administrator.gc.restApi.connection.HttpConnection;
import com.example.administrator.gc.restApi.service.CommentService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 16/8/2.
 */

public class CommentApi {

    private static final String DATABASE_NAME = "PicComment";

    public static void comment(CommentModel model, Subscriber<Void> subscriber) {
        HttpConnection httpConnection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();

        Observable<Void> observable = httpConnection.getConnection().create(CommentService.class).comment(DATABASE_NAME, model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getComment(String url, Subscriber<GetCommentModel> subscriber) {
        HttpConnection httpConnection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();

        StringBuilder sb = new StringBuilder();
        sb.append("{\"imageUrl\":\"");
        sb.append(url + "\"}");
        url = sb.toString();
        Observable<GetCommentModel> observable = httpConnection.getConnection().create(CommentService.class).getComment(DATABASE_NAME, url);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void admirePic(String objectId, CommentModel model, Subscriber<Void> subscriber) {
        HttpConnection httpConnection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<Void> observable = httpConnection.getConnection().create(CommentService.class).admire(DATABASE_NAME, objectId, model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
