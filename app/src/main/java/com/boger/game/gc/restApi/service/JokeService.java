package com.boger.game.gc.restApi.service;

import com.boger.game.gc.model.JokeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liubo on 2016/6/6.
 */
public interface JokeService {
    @GET("255-1")
    Observable<JokeResponse> getJoke(@Query("page") int page,
                                     @Query("showapi_sign") String sign,
                                     @Query("type") String type,
                                     @Query("showapi_appid") String appId);
}
