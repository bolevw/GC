package com.boger.game.gc.utils;

import android.content.Context;

/**
 * Created by liubo on 2017/4/11.
 */

public class PxConvertUtils {

    public static int dip2px(int value, Context context) {
        return (int) (context.getResources().getDisplayMetrics().density * value + 0.5f);
    }
}
