package com.boger.game.gc.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by liubo on 2017/5/9.
 */

public abstract class ApiCallBack<T> extends DisposableObserver<T> {

    public ApiCallBack(CompositeDisposable com) {
        com.add(this);
    }

    @Override
    public void onNext(T model) {
        onSuccess(model);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T data);

    protected abstract void onFail(Throwable e);
}
