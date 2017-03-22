package com.boger.game.gc.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseApplication;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ToastUtils {
    private static Toast toast;

    public static void showNormalToast(String message) {
        showNormalToast(BaseApplication.getContext(), message);
    }

    public static void showNormalToast(Context context, String message) {
        cancelToast();
        View v = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView textView = (TextView) v.findViewById(R.id.toastTextView);
        textView.setText(message);
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(v);
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
        toast = null;
    }
}
