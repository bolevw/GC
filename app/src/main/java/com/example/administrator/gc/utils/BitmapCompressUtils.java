package com.example.administrator.gc.utils;

import android.graphics.Bitmap;
import android.view.View;

import java.io.ByteArrayOutputStream;

/**
 * Created by liubo on 16/7/21.
 */

public class BitmapCompressUtils {

    public static byte[] compress(Bitmap bitmap, int targetSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        long size = baos.toByteArray().length / 1024;
        int quality = 100;
        while (baos.toByteArray().length / 1024 > targetSize) {
            baos.reset();
            quality = quality - 4;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        return baos.toByteArray();
    }

    public static void s() {
        Bitmap bitmap = null;

        byte[] bytes = compress(bitmap, 75);
        View view = new View(null);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();

    }
}
