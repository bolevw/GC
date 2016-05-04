package com.example.administrator.gc.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/5/4.
 */
public class Cache {
    private static Cache instance;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static Cache getInstance(Context context) {
        if (instance == null) {
            instance = new Cache(context);
        }

        return instance;
    }

    private Cache(Context context) {
        sharedPreferences = context.getSharedPreferences("Gc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveStringValue(String name, String value) {
        editor.putString(name, value).commit();
    }

    public String readStringValue(String name, String defValue) {
        return sharedPreferences.getString(name, defValue);
    }

    public void saveBooleanValue(String name, boolean value) {
        editor.putBoolean(name, value).commit();
    }

    public boolean readBooleanValue(String name, boolean defValue) {
        return sharedPreferences.getBoolean(name, defValue);
    }


}
