package com.example.administrator.gc.utils;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.gc.base.BaseApplication;

/**
 * Created by Administrator on 2016/3/25.
 */
public class PicassoUtils {

    public static void normalShowImage(String urls, ImageView imageView) {
        Log.d("urls", "urls:" + urls);
        Glide.with(BaseApplication.getContext())
                .load(urls)
                .fitCenter()
                .into(imageView);
    }

    public static void normalShowImage(String urls, final ImageView imageView, final Callback callback) {
        Glide.with(BaseApplication.getContext())
                .load(urls)
                .fitCenter()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                        callback.loadSuccess();
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        callback.loadFail();
                    }
                })
        ;
    }


    public interface Callback {
        void loadSuccess();

        void loadFail();
    }
}
