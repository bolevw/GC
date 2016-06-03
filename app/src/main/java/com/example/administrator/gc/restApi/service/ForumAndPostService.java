package com.example.administrator.gc.restApi.service;

import com.example.administrator.gc.model.FollowPostModel;
import com.example.administrator.gc.model.FollowResponse;
import com.example.administrator.gc.model.GetFollowPostResponse;
import com.example.administrator.gc.model.IsFollowResponse;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Observable<FollowResponse> follow(@Path("class") String name, @Body FollowPostModel model);

    @GET("1.1/classes/{class}")
    Observable<IsFollowResponse> isFollow(@Path("class") String name, @Query("where") String query);

    @DELETE("1.1/classes/{class}/{objectId}")
    Observable<Void> cancelFollow(@Path("class") String name, @Path("objectId") String objectId);

    @GET("1.1/classes/{class}")
    Observable<GetFollowPostResponse> getFollow(@Path("class") String name, @Query("where") String query);

}
