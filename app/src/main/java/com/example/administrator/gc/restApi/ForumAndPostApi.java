package com.example.administrator.gc.restApi;

import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.FollowPostModel;
import com.example.administrator.gc.restApi.connection.HttpConnection;
import com.example.administrator.gc.restApi.service.ForumAndPostService;
import com.example.administrator.gc.ui.activity.PostDetailActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 5/18/16.
 */
public class ForumAndPostApi {

    public static void followPost(FollowPostModel model, BaseSub<Void, PostDetailActivity> sub) {

        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<Void> observable = connection.getConnection().create(ForumAndPostService.class).follow("FollowPost", model);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);


    }
}
