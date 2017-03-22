package com.boger.game.gc.utils;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/3/25.
 */
public class ImageLoaderUtils {

    public static void load(String urls, ImageView imageView) {
        ImageLoad.load(urls, imageView);
    }

    public static void load(String urls, ImageLoad.ImageLoadCallback callback) {
        ImageLoad.load(urls, callback);
    }
}
