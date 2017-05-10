package com.boger.game.gc.api.Service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface VideoService {


    @GET("vppp.swf")
    Observable<String> getVideo(@Query("v") String v);
}
