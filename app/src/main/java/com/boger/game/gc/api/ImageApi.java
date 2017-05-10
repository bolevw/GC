package com.boger.game.gc.api;

import android.util.Log;

import com.boger.game.gc.api.web.GetWebObservable;
import com.boger.game.gc.base.ApiCallBack;

import org.jsoup.nodes.Document;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liubo on 2017/5/3.
 */

public class ImageApi {

    private static final String TAG = "ImageApi";

    public static void getImageCoverList(String url, ApiCallBack<Void> callBack) {
        GetWebObservable
                .getInstance(url)
                .map(new Function<Document, Void>() {
                    @Override
                    public Void apply(@NonNull Document document) throws Exception {
                        Log.d(TAG, "call: " + document.body().toString());
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);

    }
}
