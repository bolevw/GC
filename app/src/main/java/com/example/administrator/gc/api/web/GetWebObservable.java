package com.example.administrator.gc.api.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/24.
 */
public class GetWebObservable {

    public static rx.Observable getInstance(final String urls) {
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
        return observable;
    }
}
