package com.boger.game.gc.restApi;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.BaseApplication;
import com.boger.game.gc.cache.Cache;
import com.boger.game.gc.model.RegisterModel;
import com.boger.game.gc.model.SaveAvatar;
import com.boger.game.gc.model.UserModel;
import com.boger.game.gc.restApi.client.OkClient;
import com.boger.game.gc.restApi.connection.HttpConnection;
import com.boger.game.gc.restApi.service.UserService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liubo on 2016/5/17.
 */
public class UserApi {


    public static void register(RegisterModel model, ApiCallBack<UserModel> sub) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OkClient.getInstance())
                    .baseUrl(Urls.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Observable<UserModel> observable = retrofit.create(UserService.class).register(model);

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(sub);
        } catch (Exception e) {
            sub.onError(e);
        }
    }

    public static void login(String username, String password, ApiCallBack<UserModel> sub) {
        try {
            HttpConnection connection = new HttpConnection
                    .Builder(Urls.BASE_URL)
                    .build();
            Observable<UserModel> observable = connection.getConnection().create(UserService.class).login(username, password);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(sub);
        } catch (Exception e) {
            sub.onError(e);
        }
    }

    public static void saveUserAvatar(String url, String id, ApiCallBack<Void> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .client(OkClient.getUpdateUserClient(Cache.getInstance(BaseApplication.getContext()).readStringValue(UserModel.SESSIONTOKEN, "")))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Observable<Void> observable = retrofit.create(UserService.class).saveAvatar(new SaveAvatar(url), id);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
