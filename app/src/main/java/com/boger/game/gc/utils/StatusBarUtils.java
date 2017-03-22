package com.boger.game.gc.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorRes;

/**
 * Created by liubo on 2017/3/22.
 */

public class StatusBarUtils {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, @ColorRes int res) {
        if (res == 0) {
            return;
        }

    }
}
