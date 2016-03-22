package com.example.administrator.gc.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/3/22.
 */
public class BaseApplication extends Application {

    private static Context applicationContext;


    public static Context getContext() {
        return applicationContext;
    }

    public static void setContext(Context context) {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }
}
