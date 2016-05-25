package com.example.administrator.gc.api.web;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;
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
        final rx.Observable documentObservable = rx.Observable.create(new rx.Observable.OnSubscribe<Document>() {
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
        documentObservable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(final Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable instanceof UnknownHostException) {
                            return Observable.error(throwable);
                        }
                        return Observable.timer(5, TimeUnit.SECONDS);

                    }
                });

            }
        }).delay(5, TimeUnit.SECONDS);
        return documentObservable;
    }
}
