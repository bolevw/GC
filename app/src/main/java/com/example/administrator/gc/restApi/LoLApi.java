package com.example.administrator.gc.restApi;

import android.text.TextUtils;

import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.CommonHeroModel;
import com.example.administrator.gc.model.HeroMessageModel;
import com.example.administrator.gc.model.LevelForecastModel;
import com.example.administrator.gc.model.LevelModel;
import com.example.administrator.gc.model.PlayerInfoModel;
import com.example.administrator.gc.model.RecordModel;
import com.example.administrator.gc.restApi.connection.HttpConnection;
import com.example.administrator.gc.restApi.service.LoLService;
import com.example.administrator.gc.ui.fragment.CommonHeroFragment;
import com.example.administrator.gc.ui.fragment.ForecastFragment;
import com.example.administrator.gc.ui.fragment.HeroMessageFragment;
import com.example.administrator.gc.ui.fragment.LevelFragment;
import com.example.administrator.gc.ui.fragment.RecordFragment;
import com.example.administrator.gc.ui.fragment.SearchUserInfoFragment;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2016/5/27.
 */
public class LoLApi {
    public static HttpConnection getHttp(String url) {
        if (TextUtils.isEmpty(url)) {
            HttpConnection httpConnection = new HttpConnection.Builder(Urls.LOL_BASE_URL)
                    .build();
            return httpConnection;
        } else {
            HttpConnection httpConnection = new HttpConnection.Builder(url)
                    .build();
            return httpConnection;
        }
    }

    public static void getPlayerInfo(String serverName, String playerName, BaseSub<PlayerInfoModel, SearchUserInfoFragment> sub) {
        HttpConnection httpConnection = new HttpConnection.Builder(Urls.LOL_BASE_URL)
                .build();

        Observable<PlayerInfoModel> observable = httpConnection.getConnection().create(LoLService.class).getPlayerInfo(serverName, playerName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void getRecord(String serverName, String playerName, BaseSub<RecordModel, RecordFragment> sub) {
        HttpConnection connection = getHttp(null);
        Observable<RecordModel> observable = connection.getConnection().create(LoLService.class).getRecord(serverName, playerName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void getCommonHero(String serverName, String playerName, BaseSub<CommonHeroModel, CommonHeroFragment> sub) {
        HttpConnection connection = getHttp(null);
        Observable<CommonHeroModel> observable = connection.getConnection().create(LoLService.class).getCommonHero(serverName, playerName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void getHeroMessage(String serverName, String playerName, BaseSub<HeroMessageModel, HeroMessageFragment> sub) {
        HttpConnection connection = getHttp("http://lolbox.duowan.com/new/api/");
        Observable<HeroMessageModel> observable = connection.getConnection().create(LoLService.class).getHeroMessage(serverName, playerName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void getPlayerLevel(String serverName, String playerName, BaseSub<LevelModel, LevelFragment> sub) {
        HttpConnection connection = getHttp(null);
        Observable<LevelModel> observable = connection.getConnection().create(LoLService.class).getLevel(serverName, playerName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public static void getLevelForecast(String serverName, String playerName, BaseSub<LevelForecastModel, ForecastFragment> sub) {
        HttpConnection connection = getHttp(null);
        Observable<LevelForecastModel> observable = connection.getConnection().create(LoLService.class).getForecast(serverName, playerName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

}
