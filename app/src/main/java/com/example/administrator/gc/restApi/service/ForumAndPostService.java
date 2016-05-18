package com.example.administrator.gc.restApi.service;

import com.example.administrator.gc.model.FollowPostModel;
import com.example.administrator.gc.model.IsFollowResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liubo on 5/18/16.
 */
public interface ForumAndPostService {


    @POST("1.1/classes/{class}")
    Observable<Void> follow(@Path("class") String name, @Body FollowPostModel model);

    @GET("1.1/classes/{class}")
    Observable<IsFollowResponse> isFollow(@Path("class") String name, @Query("where") String query);

}
