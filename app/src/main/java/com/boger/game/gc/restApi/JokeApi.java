package com.boger.game.gc.restApi;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.JokeResponse;
import com.boger.game.gc.restApi.connection.HttpConnection;
import com.boger.game.gc.restApi.service.JokeService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by liubo on 2016/6/6.
 */
public class JokeApi {
    public static void getJoke(int page, ApiCallBack<JokeResponse> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.JOKE_BASE_URL)
                .build();
        Observable<JokeResponse> observable = connection.getConnection().create(JokeService.class).getJoke(
                page,
                Urls.JOKE_KEY,
                "29",
                Urls.JOKE_ID
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
