package com.boger.game.gc.api;

import android.util.Log;

import com.boger.game.gc.api.web.GetWebObservable;

import org.jsoup.nodes.Document;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2017/5/3.
 */

public class ImageApi {

    private static final String TAG = "ImageApi";

    public static void getImageCoverList(String url, Subscriber<Void> subscriber) {
        GetWebObservable
                .getInstance(url)
                .map(new Func1<Document, Void>() {
                    @Override
                    public Void call(Document document) {
                        Log.d(TAG, "call: " + document.body().toString());
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
}
