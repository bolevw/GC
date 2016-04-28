package com.example.administrator.gc.api.Service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface VideoService {


    @GET("vppp.swf")
    Observable<String> getVideo(@Query("v") String v);
}
