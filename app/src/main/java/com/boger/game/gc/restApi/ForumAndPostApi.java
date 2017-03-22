package com.boger.game.gc.restApi;

import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.AttentionQueryModel;
import com.boger.game.gc.model.FollowPostModel;
import com.boger.game.gc.model.FollowResponse;
import com.boger.game.gc.model.GetFollowPostResponse;
import com.boger.game.gc.model.IsFollowModel;
import com.boger.game.gc.model.IsFollowResponse;
import com.boger.game.gc.restApi.connection.HttpConnection;
import com.boger.game.gc.restApi.service.ForumAndPostService;
import com.boger.game.gc.ui.activity.PostDetailActivity;
import com.boger.game.gc.ui.fragment.childfragment.AttentionPostFragment;
import com.google.gson.Gson;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 5/18/16.
 */
public class ForumAndPostApi {

    public static void followPost(FollowPostModel model, BaseSub<FollowResponse, PostDetailActivity> sub) {

        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<FollowResponse> observable = connection.getConnection().create(ForumAndPostService.class).follow("FollowPost", model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void isFollow(IsFollowModel model, BaseSub<IsFollowResponse, PostDetailActivity> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();

        Observable<IsFollowResponse> observable = connection.getConnection().create(ForumAndPostService.class).isFollow("FollowPost", new Gson().toJson(model));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void cancelFollow(String id, BaseSub<Void, PostDetailActivity> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<Void> observable = connection.getConnection().create(ForumAndPostService.class).cancelFollow("FollowPost", id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }


    public static void getFollowPost(String userId, BaseSub<GetFollowPostResponse, AttentionPostFragment> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.BASE_URL)
                .build();
        Observable<GetFollowPostResponse> observable = connection.getConnection().create(ForumAndPostService.class).getFollow("FollowPost", new Gson().toJson(new AttentionQueryModel(userId)));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
