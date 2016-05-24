package com.example.administrator.gc.api.web;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/3/24.
 */
public class GetWebObservable {

    public static rx.Observable getInstance(final String urls) {
        Log.d("webUrls", urls);
        rx.Observable observable = rx.Observable.create(new rx.Observable.OnSubscribe<Document>() {
            @Override
            public void call(Subscriber<? super Document> subscriber) {
                try {
                    Document document = Jsoup.connect(urls).get();
                    subscriber.onNext(document);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        });
        observable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<Document>>() {
            @Override
            public Observable<Document> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<Document>>() {
                    @Override
                    public Observable<Document> call(Throwable throwable) {
                        if (throwable instanceof SocketTimeoutException) {
                            return Observable.timer(5, TimeUnit.SECONDS).just(null);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                });

            }
        }).delay(5, TimeUnit.SECONDS);
        return observable;
    }
}
