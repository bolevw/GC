package com.example.administrator.gc.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.administrator.gc.base.BaseApplication;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/3/25.
 */
public class PicassoUtils {

    public static void normalShowImage(String url, ImageView imageView, Callback callback) {
        Picasso.with(BaseApplication.getContext())
                .load(url)
                .fit()
                .into(imageView, callback);
    }

    public static void normalShowImage(Context context, String urls, ImageView imageView) {
        Picasso
                .with(BaseApplication.getContext())
                .load(urls)
                .fit()
                .into(imageView);
    }

    public static void normalShowImage(Context context, String urls, ImageView imageView, Callback callback) {
        Picasso.with(BaseApplication.getContext())
                .load(urls)
                .fit()
                .into(imageView, callback);
    }

}
