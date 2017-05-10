package com.boger.game.gc.restApi;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.CommentModel;
import com.boger.game.gc.model.GetCommentModel;
import com.boger.game.gc.restApi.connection.HttpConnection;
import com.boger.game.gc.restApi.service.CommentService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by liubo on 16/8/2.
 */

public class CommentApi {

    private static final String DATABASE_NAME = "PicComment";

    public static void comment(CommentModel model, ApiCallBack<Void> subscriber) {
        HttpConnection httpConnection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();

        Observable<Void> observable = httpConnection.getConnection().create(CommentService.class).comment(DATABASE_NAME, model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getComment(String url, ApiCallBack<GetCommentModel> subscriber) {
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

    public static void admirePic(String objectId, CommentModel model, ApiCallBack<Void> subscriber) {
        HttpConnection httpConnection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<Void> observable = httpConnection.getConnection().create(CommentService.class).admire(DATABASE_NAME, objectId, model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
