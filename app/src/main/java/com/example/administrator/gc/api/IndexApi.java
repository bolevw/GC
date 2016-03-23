package com.example.administrator.gc.api;

import com.example.administrator.gc.api.Service.IndexService;
import com.example.administrator.gc.utils.StringConverter;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/23.
 */
public class IndexApi {

    public static void getIndex(final String url, Subscriber<String> subscriber) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new StringConverter())
                .build();

        Observable<String> observable = retrofit.create(IndexService.class).get();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
