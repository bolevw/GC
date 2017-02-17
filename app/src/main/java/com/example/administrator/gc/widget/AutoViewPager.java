package com.example.administrator.gc.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by liubo on 2017/2/16.
 */

public class AutoViewPager extends ViewPager {

    public AutoViewPager(Context context) {
        super(context);
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void start() {
        final int[] i = {this.getCurrentItem()};
        Observable.interval(4, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        i[0]++;
                        AutoViewPager.this.setCurrentItem(i[0]);
                    }
                });
    }
}
