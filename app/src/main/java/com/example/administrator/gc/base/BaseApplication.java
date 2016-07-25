package com.example.administrator.gc.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/22.
 */
public class BaseApplication extends Application {

    private static Context applicationContext;
    private RefWatcher refWatcher;


    public static Context getContext() {
        return applicationContext;
    }

    public static void setContext(Context context) {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DEVICE_MESSAGE", Build.DEVICE + "-" + Build.DISPLAY + "-" + new Date(Build.TIME));
        refWatcher = LeakCanary.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("lowMemory", "onLowMemory");
    }
}
