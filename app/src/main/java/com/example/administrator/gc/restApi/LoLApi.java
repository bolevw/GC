package com.example.administrator.gc.restApi;

import android.text.TextUtils;

import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.PlayerInfoModel;
import com.example.administrator.gc.model.RecordModel;
import com.example.administrator.gc.restApi.connection.HttpConnection;
import com.example.administrator.gc.restApi.service.LoLService;
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
}
