package com.example.administrator.gc.restApi.service;

import com.example.administrator.gc.model.JokeResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liubo on 2016/6/6.
 */
public interface JokeService {
    @GET("QueryJokeByTime")
    Observable<JokeResponse> getJoke(@Query("key") String key , @Query("time") String time, @Query("sort")String sort, @Query("page")Integer page, @Query("rows")Integer rows);
}
