package com.boger.game.gc.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liubo on 2017/2/16.
 */

public class AutoViewPager extends ViewPager {
    private int defaultPosition = 0;
    private Disposable subscription;
    private boolean start;

    public AutoViewPager(Context context) {
        super(context);
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start = false;

    }

    public void start() {
        if (!start) {
            startSubscription();
            start = true;
        }
    }

    private void startSubscription() {
        setCurrentItem(getCurrentItem());
        subscription = Observable
                .interval(4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        int index = getCurrentItem();
                        index++;
                        if (index >= getAdapter().getCount()) {
                            index = defaultPosition;
                        }
                        setCurrentItem(index);
                    }

                });
    }


    @Override
    protected void onDetachedFromWindow() {
        start = false;
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
        super.onDetachedFromWindow();
    }
}
