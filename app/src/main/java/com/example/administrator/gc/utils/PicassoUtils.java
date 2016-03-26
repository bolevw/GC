package com.example.administrator.gc.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/3/25.
 */
public class PicassoUtils {
    private static Picasso picasso;

    public static void normalShowImage(Context context, String urls, ImageView imageView) {
        picasso
                .with(context)
                .load(urls)
                .fit()
                .into(imageView);
    }

}
