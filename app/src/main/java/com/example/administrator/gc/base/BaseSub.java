package com.example.administrator.gc.base;

import android.util.Log;

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
            Log.e("error", e.toString());
            error();
        }
    }

    @Override
    public void onNext(T t) {
        if (view != null) {
            next(t);
        }
    }

    protected abstract void error();

    protected abstract void next(T t);
}
