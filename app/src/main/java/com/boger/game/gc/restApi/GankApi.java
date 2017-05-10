package com.boger.game.gc.restApi;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.PictureListModel;
import com.boger.game.gc.restApi.connection.HttpConnection;
import com.boger.game.gc.restApi.service.GankService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by liubo on 2016/6/3.
 */
public class GankApi {
    public static void getPicture(Integer index, ApiCallBack<PictureListModel> sub) {
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
