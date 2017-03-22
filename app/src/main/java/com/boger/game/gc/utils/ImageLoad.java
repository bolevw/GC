package com.boger.game.gc.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.boger.game.gc.base.BaseApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by liubo on 2017/3/22.
 */

public class ImageLoad {
    public static void load(String url, ImageView target) {
        Glide
                .with(BaseApplication.getContext())
                .load(url)
                .into(target);
    }

    public static void load(String url, final ImageLoadCallback callback) {
        Glide
                .with(BaseApplication.getContext())
                .load(url)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        callback.loadSuccess(resource);
                    }


                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        callback.loadFail(e);
                    }
                });
    }


    public interface ImageLoadCallback {
        void loadSuccess(Drawable resource);

        void loadFail(Exception e);
    }
}
