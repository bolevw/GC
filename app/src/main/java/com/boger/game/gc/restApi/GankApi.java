package com.boger.game.gc.restApi;

import com.boger.game.gc.model.PictureListModel;
import com.boger.game.gc.restApi.connection.HttpConnection;
import com.boger.game.gc.restApi.service.GankService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2016/6/3.
 */
public class GankApi {
    public static void getPicture(Integer index, Subscriber<PictureListModel> sub) {
        HttpConnection connection = new HttpConnection.Builder(Urls.GANK_BASE_URL)
                .build();

        Observable<PictureListModel> observable = connection.getConnection().create(GankService.class).getPicture(index);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }


    public static void getComment() {
        /*HttpConnection connection = new HttpConnection.Builder()
                .build();*/
    }
}
