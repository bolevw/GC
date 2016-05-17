package com.example.administrator.gc.base;

import android.util.Log;

import com.example.administrator.gc.model.ErrorBodyModel;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/12.
 */
public abstract class BaseSub<T, V> extends Subscriber<T> {

    V view;

    public BaseSub(V view) {
        this.view = view;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (view != null) {
            String message = "message";
            Log.e("error", e.toString());
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                try {
                    String errorMessage = httpException.response().errorBody().string();
                    int code = httpException.code();
                    if (code == 400) {
                        ErrorBodyModel ebdy = new Gson().fromJson(errorMessage, ErrorBodyModel.class);
                        message = ebdy.getError();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            error(message);
        }
    }

    @Override
    public void onNext(T t) {
        if (view != null) {
            next(t);
        }
    }

    protected abstract void error(String e);

    protected abstract void next(T t);
}
