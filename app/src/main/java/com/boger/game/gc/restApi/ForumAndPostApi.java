package com.boger.game.gc.restApi;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.AttentionQueryModel;
import com.boger.game.gc.model.FollowPostModel;
import com.boger.game.gc.model.FollowResponse;
import com.boger.game.gc.model.GetFollowPostResponse;
import com.boger.game.gc.model.IsFollowModel;
import com.boger.game.gc.model.IsFollowResponse;
import com.boger.game.gc.restApi.connection.HttpConnection;
import com.boger.game.gc.restApi.service.ForumAndPostService;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by liubo on 5/18/16.
 */
public class ForumAndPostApi {

    public static void followPost(FollowPostModel model, ApiCallBack<FollowResponse> sub) {

        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<FollowResponse> observable = connection.getConnection().create(ForumAndPostService.class).follow("FollowPost", model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void isFollow(IsFollowModel model, ApiCallBack<IsFollowResponse> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();

        Observable<IsFollowResponse> observable = connection.getConnection().create(ForumAndPostService.class).isFollow("FollowPost", new Gson().toJson(model));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void cancelFollow(String id, ApiCallBack<Void> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<Void> observable = connection.getConnection().create(ForumAndPostService.class).cancelFollow("FollowPost", id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }


    public static void getFollowPost(String userId, ApiCallBack<GetFollowPostResponse> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<GetFollowPostResponse> observable = connection.getConnection().create(ForumAndPostService.class).getFollow("FollowPost", new Gson().toJson(new AttentionQueryModel(userId)));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
