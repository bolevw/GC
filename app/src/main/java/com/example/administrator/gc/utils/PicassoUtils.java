package com.example.administrator.gc.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.gc.base.BaseApplication;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/3/25.
 */
public class PicassoUtils {

    public static void normalShowImage(String url, ImageView imageView, Callback callback) {
        Picasso.with(BaseApplication.getContext().getApplicationContext())
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .fit()
                .into(imageView, callback);
    }

    public static void normalShowImage(Context context, String urls, ImageView imageView) {
        Log.d("urls", "urls:" + urls);
        Picasso
                .with(BaseApplication.getContext().getApplicationContext())
                .load(urls)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .fit()
                .into(imageView);
    }

    public static void normalShowImage(Context context, String urls, ImageView imageView, Callback callback) {
        Picasso.with(BaseApplication.getContext().getApplicationContext())
                .load(urls)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .fit()
                .into(imageView, callback);
    }

}
