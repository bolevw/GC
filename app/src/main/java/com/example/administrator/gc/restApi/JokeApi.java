package com.example.administrator.gc.restApi;

import com.example.administrator.gc.model.JokeResponse;
import com.example.administrator.gc.restApi.connection.HttpConnection;
import com.example.administrator.gc.restApi.service.JokeService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokeApi {
    public static void getJoke(String time, int index, Subscriber<JokeResponse> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.JOKE_BASE_URL)
                .build();
        Observable<JokeResponse> observable = connection.getConnection().create(JokeService.class).getJoke(Urls.JOKE_KEY, time, "asc", index, 40);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
