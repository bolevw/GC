package com.example.administrator.gc.restApi;

import android.os.Environment;
import android.util.Log;

import com.example.administrator.gc.base.BaseApplication;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.ui.activity.PhotoActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2016/5/20.
 */
public class DownLoad {
    public static void downLoadImage(final String url, final BaseSub<String, PhotoActivity> sub) {

      /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
 Observable<ResponseBody> response = retrofit.create(DownLoadService.class).downLoadPic();

        response.map(new Func1<ResponseBody, String>() {
            @Override
            public String call(ResponseBody responseBody) {
                try {
                    File file = new File(Environment.getExternalStorageDirectory().getPath() + System.currentTimeMillis() + ".jpeg");
                    Log.d("filePath", file.getPath());
                    InputStream inputStream = null;
                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    byte[] bytes = new byte[1024];
                    while (inputStream.read(bytes) != -1) {
                        fileOutputStream.write(bytes, 0, inputStream.read(bytes));
                    }

                    inputStream.close();
                    fileOutputStream.flush();
                    sub.onNext(file.getPath());

                } catch (Exception e) {
                    sub.onError(e);
                }
                return null;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);*/

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    URL mUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
                    connection.setConnectTimeout(5 * 1000);
                    connection.setReadTimeout(5 * 1000);
                    InputStream inputStream = connection.getInputStream();

                    File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                    Log.d("filePath", filePath.getPath());
                    if (!filePath.exists()) {
                        filePath.mkdir();
                    }
                    filePath.createNewFile();

                    String fileName = System.currentTimeMillis() + ".jpeg";
                    File picFile = null;

                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        picFile = new File(filePath + fileName);
                    } else {
                        picFile = new File(BaseApplication.getContext().getExternalFilesDir(
                                Environment.DIRECTORY_PICTURES), fileName);
                    }

                    if (!picFile.exists()) {
                        picFile.mkdir();
                    }
                    picFile.createNewFile();

                    OutputStream fos = new FileOutputStream(picFile);
                    byte[] bytes = new byte[1024];
                    int read = inputStream.read(bytes);
                    while (read != -1) {
                        fos.write(bytes, 0, read);
                    }

                    subscriber.onNext(filePath.getPath());
                    inputStream.close();
                    fos.flush();
                    fos.close();
                } catch (MalformedURLException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                } catch (IOException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);


    }

    private boolean hasSDCard() {
        // 获取外部存储的状态
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 有SD卡
            return true;
        }
        return false;
    }
}
