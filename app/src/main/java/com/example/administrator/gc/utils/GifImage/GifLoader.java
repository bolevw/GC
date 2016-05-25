package com.example.administrator.gc.utils.GifImage;

import android.content.Context;
import android.net.Uri;

import com.example.administrator.gc.restApi.DownLoad;
import com.example.administrator.gc.utils.Md5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by liubo on 2016/5/24.
 */
public class GifLoader {

    public static final String TAG = "GifLoader";
    private static Context c;

    public static class Gif {
        public static GifLoader loader = new GifLoader();
    }

    public static GifLoader with(Context context) {
        c = context;
        return Gif.loader;
    }

    public GifDrawer load(InputStream is) {
        GifDrawer drawer = new GifDrawer();
        try {
            drawer.setIs(is);
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

    public GifDrawer load(String url) {

        final InputStream[] is = {null};
        try {
            String name = Md5Utils.getMD5(url);
            String path = c.getExternalCacheDir() + File.separator + name;

            File file = new File(path);
            if (file.exists()) {
                is[0] = new FileInputStream(file);
                return load(is[0]);
            } else {

                DownLoad.downLoad(url, new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
//                        GifLoader.with(c).load(responseBody.byteStream()).into();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return load(is[0]);
    }
}
