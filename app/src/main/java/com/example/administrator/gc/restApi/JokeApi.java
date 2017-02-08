package com.example.administrator.gc.restApi;

import com.example.administrator.gc.model.JokeResponse;
import com.example.administrator.gc.restApi.connection.HttpConnection;
import com.example.administrator.gc.restApi.service.JokeService;
import com.example.administrator.gc.utils.Md5;

import java.security.NoSuchAlgorithmException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokeApi {
    public static void getJoke(int page, Subscriber<JokeResponse> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.JOKE_BASE_URL)
                .build();
        Observable<JokeResponse> observable = connection.getConnection().create(JokeService.class).getJoke(
                page,
                Urls.JOKE_ID,
                "29",
                makeSignRequest(page)
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }


    public static String makeSignRequest(int page) {
        String sign = null;

        String appid = "page" + page + "showapi_appid" + Urls.JOKE_ID + "type29";
        sign = "showapi_sign" + Urls.JOKE_KEY;
        sign = appid + sign;
        try {
            sign = Md5.getMD5(sign);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return sign;
    }
}
