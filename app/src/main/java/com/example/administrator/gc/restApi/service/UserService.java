package com.example.administrator.gc.restApi.service;

import com.example.administrator.gc.model.RegisterModel;
import com.example.administrator.gc.model.SaveAvatar;
import com.example.administrator.gc.model.UserModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

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
