package com.example.administrator.gc.api.Service;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2016/3/23.
 */
public interface IndexService {

    @GET("/")
    Observable<String> get();
}
