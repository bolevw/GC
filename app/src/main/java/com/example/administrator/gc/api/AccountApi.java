package com.example.administrator.gc.api;

import android.util.Log;

import com.example.administrator.gc.api.web.GetWebObservable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/29.
 */
public class AccountApi {


    private static final String TAG = "AccountApi";

    public static void getLogin(String url, Subscriber<String> subscriber) {
        GetWebObservable.getInstance(url).map(new Func1<Document, String>() {
            @Override
            public String call(Document document) {
                Element bodyEl = document.body();
                Log.d(TAG, "body - >" + bodyEl.toString());
                return document.toString();
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
