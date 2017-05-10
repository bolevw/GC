package com.boger.game.gc.restApi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.BaseApplication;
import com.boger.game.gc.restApi.Converter.ScalarsConverterFactory;
import com.boger.game.gc.restApi.service.DownLoadService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by liubo on 2016/5/20.
 */
public class DownLoad {
    public static void downLoadImage(final Context context, final String url, final ApiCallBack<String> sub) {


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                try {
                    URL mUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
                    connection.setConnectTimeout(5 * 1000);
                    connection.setReadTimeout(5 * 1000);
                    InputStream inputStream = connection.getInputStream();
                    String imageName = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + System.currentTimeMillis() + "_temp.jpeg";

                    File newFile = new File(imageName);
                    if (newFile.exists()) {
                        newFile.delete();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    FileOutputStream fosm = new FileOutputStream(newFile);
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fosm)) {
                        fosm.flush();
                        fosm.close();
                    }
                    emitter.onNext(newFile.getPath());
                    emitter.onComplete();
                } catch (MalformedURLException e) {
                    emitter.onError(e);
                    e.printStackTrace();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }

            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);


    }

    private static void logFileSize(File picFile) {
        StatFs statFs = new StatFs(picFile.getPath());
        long fileSize = statFs.getFreeBlocksLong();
        long avSize = statFs.getAvailableBlocksLong();
        long s = statFs.getBlockCountLong();
        String fileS = Formatter.formatFileSize(BaseApplication.getContext(), fileSize);
        String aS = Formatter.formatFileSize(BaseApplication.getContext(), avSize);
        String cS = Formatter.formatFileSize(BaseApplication.getContext(), s);

        Log.d("fileSize", "freeS:" + fileS + " available size:" + aS + " blockSize:" + cS);
    }

    private boolean hasSDCard() {
        // 获取外部存储的状态
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static void downLoad(final String url, ApiCallBack<ResponseBody> subscriber) {
        InputStream stream = null;
        String baseUrl = url.substring(0, url.lastIndexOf("/"));
        String imageUrl = url.substring(url.lastIndexOf("/") + 1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Observable<ResponseBody> observable = retrofit.create(DownLoadService.class).downLoadPic(imageUrl);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
