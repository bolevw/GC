package com.boger.game.gc.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by liubo on 2017/2/16.
 */

public class AutoViewPager extends ViewPager {
    private int defaultPosition = 0;
    private Subscription subscription;
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
        subscription = Observable.interval(4, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
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
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDetachedFromWindow();
    }
}
