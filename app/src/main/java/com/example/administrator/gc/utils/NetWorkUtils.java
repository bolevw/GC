package com.example.administrator.gc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by liubo on 2017/2/17.
 */

public class NetWorkUtils {

    public static boolean hasNetWork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.isAvailable()) {
                    return true;
                }
            }
        }
        cm = null;
        return false;
    }


    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null && networkInfo.isConnected()) {
                return networkInfo.isAvailable();
            }
        }

        return false;
    }

    public static boolean isMobileAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null && info.isConnected()) {
                return info.isAvailable();
            }
        }
        return false;
    }

    public static String getNetWorkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return "暂无网络";
        }

        int type = info.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return "WI-FI网络";
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (subType == TelephonyManager.NETWORK_TYPE_UMTS && !tm.isNetworkRoaming()) {
                return "3G";
            } else {
                return "2G";
            }
        } else {
            return "未知网络";
        }

    }
}
