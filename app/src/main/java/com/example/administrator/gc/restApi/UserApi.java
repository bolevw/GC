package com.example.administrator.gc.restApi;

import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.RegisterModel;
import com.example.administrator.gc.model.UserModel;
import com.example.administrator.gc.restApi.client.OkClient;
import com.example.administrator.gc.restApi.connection.HttpConnection;
import com.example.administrator.gc.restApi.service.UserService;
import com.example.administrator.gc.ui.activity.LoginActivity;
import com.example.administrator.gc.ui.activity.RegisterActivity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2016/5/17.
 */
public class UserApi {


    public static void register(RegisterModel model, BaseSub<UserModel, RegisterActivity> sub) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OkClient.getInstance())
                    .baseUrl(Urls.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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

    public static void login(String username, String password, BaseSub<UserModel, LoginActivity> sub) {
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

}
