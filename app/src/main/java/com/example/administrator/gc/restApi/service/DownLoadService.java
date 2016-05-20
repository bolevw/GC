package com.example.administrator.gc.restApi.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liubo on 2016/5/20.
 */
public interface DownLoadService {

    @GET("/")
    Observable<ResponseBody> downLoadPic();
}
