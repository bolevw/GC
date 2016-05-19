package com.example.administrator.gc.restApi;

import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.FollowPostModel;
import com.example.administrator.gc.model.FollowResponse;
import com.example.administrator.gc.model.IsFollowModel;
import com.example.administrator.gc.model.IsFollowResponse;
import com.example.administrator.gc.restApi.connection.HttpConnection;
import com.example.administrator.gc.restApi.service.ForumAndPostService;
import com.example.administrator.gc.ui.activity.PostDetailActivity;
import com.example.administrator.gc.ui.fragment.childfragment.AttentionPostFragment;
import com.google.gson.Gson;

import java.util.List;

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


    public static void getFollowPost(String userId, BaseSub<List<FollowPostModel>, AttentionPostFragment> sub) {

    }
}
