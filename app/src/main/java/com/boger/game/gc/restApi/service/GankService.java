package com.boger.game.gc.restApi.service;

import com.boger.game.gc.model.PictureListModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by liubo on 2016/6/3.
 */
public interface GankService {
    @GET("data/福利/20/{pageIndex}")
    Observable<PictureListModel> getPicture(@Path("pageIndex") Integer index);
}
