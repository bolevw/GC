package com.boger.game.gc.restApi.service;

import com.boger.game.gc.model.RegisterModel;
import com.boger.game.gc.model.SaveAvatar;
import com.boger.game.gc.model.UserModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by liubo on 2016/5/17.
 */
public interface UserService {

    @POST("1.1/users")
    Observable<UserModel> register(@Body RegisterModel register);

    @GET("1.1/login")
    Observable<UserModel> login(@Query("username") String username, @Query("password") String password);

    @PUT("1.1/users/{objectId}")
    Observable<Void> saveAvatar(@Body SaveAvatar url, @Path("objectId") String id);
}
