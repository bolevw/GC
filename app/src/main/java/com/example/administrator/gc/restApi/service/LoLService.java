package com.example.administrator.gc.restApi.service;

import com.example.administrator.gc.model.CommonHeroModel;
import com.example.administrator.gc.model.HeroMessageModel;
import com.example.administrator.gc.model.LevelForecastModel;
import com.example.administrator.gc.model.LevelModel;
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

    @GET("/hero.php?")
    Observable<CommonHeroModel> getCommonHero(@Query("serverName") String serverName, @Query("playerName") String playerName);

    @GET("index.php?_do=personal/championslist")
    Observable<HeroMessageModel> getHeroMessage(@Query("serverName") String serverName, @Query("playerName") String playerName);

    @GET("/s5str.php?")
    Observable<LevelModel> getLevel(@Query("serverName") String serverName, @Query("playerName") String playerName);

    @GET("/rank.php?")
    Observable<LevelForecastModel> getForecast(@Query("serverName") String serverName, @Query("playerName") String playerName);
}
