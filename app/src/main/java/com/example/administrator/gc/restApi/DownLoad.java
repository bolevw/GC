package com.example.administrator.gc.restApi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import com.example.administrator.gc.base.BaseApplication;
import com.example.administrator.gc.restApi.Converter.ScalarsConverterFactory;
import com.example.administrator.gc.restApi.service.DownLoadService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubo on 2016/5/20.
 */
public class DownLoad {
    public static void downLoadImage(final Context context, final String url, final Subscriber<String> sub) {

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
                    subscriber.onNext(newFile.getPath());
                    subscriber.onCompleted();

                    /*File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
                    Log.d("filePath", filePath.getPath());
                    if (!filePath.exists()) {
                        filePath.mkdir();
                    }
//                    filePath.createNewFile();

                    String fileName = System.currentTimeMillis() + ".jpg";
                    File picFile = null;

                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        picFile = new File(filePath + fileName);
                    } else {
                        picFile = new File(BaseApplication.getContext().getFilesDir().getPath(), fileName);
                        Log.d("filePath", "getFilesDir: " + BaseApplication.getContext().getFilesDir() + "  getExternalFilesDir" + BaseApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                    }

                    if (!picFile.exists()) {
                        picFile.createNewFile();
                    }

                    OutputStream fos = new FileOutputStream(picFile);
                    byte[] bytes = new byte[4];
                    logFileSize(picFile);

                    int read = inputStream.read(bytes);
                    while (read != -1) {
                        fos.write(bytes, 0, read);
                    }

                    Bitmap bm = BitmapFactory.decodeFile(picFile.getPath());
                    bm.compress(Bitmap.CompressFormat.JPEG, 80, new BufferedOutputStream(fos));

                    subscriber.onNext(filePath.getPath());
                    inputStream.close();
                    fos.flush();
                    fos.close();
                    bm.recycle();*/
                } catch (MalformedURLException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                } catch (Exception e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.io())
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

    public static void downLoad(final String url, Subscriber<ResponseBody> subscriber) {
        InputStream stream = null;
        String baseUrl = url.substring(0, url.lastIndexOf("/"));
        String imageUrl = url.substring(url.lastIndexOf("/") + 1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Observable<ResponseBody> observable = retrofit.create(DownLoadService.class).downLoadPic(imageUrl);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
