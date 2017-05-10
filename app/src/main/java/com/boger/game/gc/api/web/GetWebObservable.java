package com.boger.game.gc.api.web;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by Administrator on 2016/3/24.
 */
public class GetWebObservable {

    public static Observable getInstance(final String urls) {
        Log.d("webUrls", urls);
        Observable observable = Observable
                .create(new ObservableOnSubscribe() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter emitter) throws Exception {
                        try {
                            Document document = Jsoup.connect(urls).get();
                            emitter.onNext(document);
                            emitter.onComplete();
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    }
                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable
                                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                                    @Override
                                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                        if (throwable instanceof UnknownHostException) {
                                            return Observable.error(throwable);
                                        } else {
                                            return Observable.timer(5, TimeUnit.SECONDS);
                                        }
                                    }
                                });
                    }
                })
                .delay(5, TimeUnit.SECONDS);

        return observable;
    }
}
