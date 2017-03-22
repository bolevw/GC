package com.boger.game.gc.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.boger.game.gc.R;

/**
 * Created by liubo on 2016/5/17.
 */
public class SnackbarUtils {
    public static void setBackground(Snackbar snackbar, Context context) {
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    public static void setBackground(Snackbar snackbar, Context context, @ColorRes int color) {
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(context, color));
    }
}
