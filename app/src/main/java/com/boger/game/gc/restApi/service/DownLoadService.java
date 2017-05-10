package com.boger.game.gc.restApi.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liubo on 2016/5/20.
 */
public interface DownLoadService {

    @GET("/")
    Observable<ResponseBody> downLoadPic();


    @GET("{fileName}")
    Observable<ResponseBody> downLoadPic(@Path("fileName") String fileName);
}
