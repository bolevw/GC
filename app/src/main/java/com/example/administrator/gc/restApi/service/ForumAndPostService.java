package com.example.administrator.gc.restApi.service;

import com.example.administrator.gc.model.FollowPostModel;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by liubo on 5/18/16.
 */
public interface ForumAndPostService {


    @POST("/classes/{class}")
    Observable<Void> follow(@Path("class") String name, @Body FollowPostModel model);

}
