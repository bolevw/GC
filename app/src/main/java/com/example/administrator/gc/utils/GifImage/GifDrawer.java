package com.example.administrator.gc.utils.GifImage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by liubo on 2016/5/24.
 */
public class GifDrawer {

    private Movie movie;
    private InputStream is;
    private Canvas canvas;
    private Bitmap mBitmap;
    private ImageView imageView;


    public void into(ImageView imageView) {
        this.imageView = imageView;

    }


    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
