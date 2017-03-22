package com.boger.game.gc.restApi;

import android.text.TextUtils;

import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.CommonHeroModel;
import com.boger.game.gc.model.HeroMessageModel;
import com.boger.game.gc.model.LevelForecastModel;
import com.boger.game.gc.model.LevelModel;
import com.boger.game.gc.model.PlayerInfoModel;
import com.boger.game.gc.model.RecordModel;
import com.boger.game.gc.restApi.connection.HttpConnection;
import com.boger.game.gc.restApi.service.LoLService;
import com.boger.game.gc.ui.fragment.CommonHeroFragment;
import com.boger.game.gc.ui.fragment.ForecastFragment;
import com.boger.game.gc.ui.fragment.HeroMessageFragment;
import com.boger.game.gc.ui.fragment.LevelFragment;
import com.boger.game.gc.ui.fragment.RecordFragment;
import com.boger.game.gc.ui.fragment.SearchUserInfoFragment;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2016/5/27.
 */
public class LoLApi {
    private static HttpConnection getHttp(String url) {
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
