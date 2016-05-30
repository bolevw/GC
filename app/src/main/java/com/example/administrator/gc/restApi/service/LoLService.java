package com.example.administrator.gc.restApi.service;

import com.example.administrator.gc.model.PlayerInfoModel;
import com.example.administrator.gc.model.RecordModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liubo on 2016/5/27.
 */
public interface LoLService {
    @GET("/playerinfo.php")
    Observable<PlayerInfoModel> getPlayerInfo(@Query("serverName") String serverName, @Query("playerName") String playName);

    @GET("/Record.php")
    Observable<RecordModel> getRecord(@Query("serverName") String serverName, @Query("playerName") String playerName);
}
