package com.example.administrator.gc.utils.GifImage;

import android.content.Context;
import android.net.Uri;

import com.example.administrator.gc.restApi.DownLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by liubo on 2016/5/24.
 */
public class GifLoader {
    private Context c;

    public GifLoader with(Context c) {
        this.c = c;
        return new GifLoader();
    }

    public GifDrawer load(InputStream is) {
        GifDrawer drawer = new GifDrawer();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawer;
    }

    public void load(Uri uri) {
        InputStream is = null;
        try {
            is = c.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        load(is);
    }

    public void load(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        load(is);
    }

    public void load(String url) {
        final InputStream[] is = {null};
        try {
            DownLoad.downLoad(url, new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    is[0] = responseBody.byteStream();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        load(is[0]);
    }
}
