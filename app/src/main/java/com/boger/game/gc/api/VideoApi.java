package com.boger.game.gc.api;

import com.boger.game.gc.api.Service.VideoService;
import com.boger.game.gc.api.http.HttpClient;
import com.boger.game.gc.api.web.GetWebObservable;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.ui.activity.VideoActivity;
import com.boger.game.gc.utils.StringConverter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/27.
 */
public class VideoApi {
    public static void getVideo(String url, BaseSub<String, VideoActivity> sub) {
        GetWebObservable.getInstance(url)
                .flatMap(new Func1<Document, Observable<String>>() {
                    @Override
                    public Observable<String> call(Document document) {
                        Element el = document.body();
                        String videoUrl = el.getElementsByAttributeValue("id", "video_embed").get(0).getElementsByTag("embed").attr("src");
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://assets.dwstatic.com/video/")
                                .client(HttpClient.getClient())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .addConverterFactory(new StringConverter())
                                .build();

                        return retrofit.create(VideoService.class).getVideo("4872481");
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

   /* public static void requestVideo(String url, Subscriber<String> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(HttpClient.getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new StringConverter())
                .build();

        rx.Observable observable = retrofit.create(VideoService.class).getVideo();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }*/
}
