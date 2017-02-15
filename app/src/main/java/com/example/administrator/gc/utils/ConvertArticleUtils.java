package com.example.administrator.gc.utils;

import android.util.Log;

import com.example.administrator.gc.model.TransformContentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 2016/5/19.
 */
public class ConvertArticleUtils {
    private static final String TAG = "ConvertArticleUtils";

    public static TransformContentModel convert(String originalArticle) {
        Log.e(TAG, "original: " + originalArticle);
        String jpg = ".jpg";
        TransformContentModel model = new TransformContentModel();
        StringBuffer sb = new StringBuffer();
        List<String> picUrls = new ArrayList<>();
        boolean flag = true;
        int picSize = 0;
        while (flag) {
            int p = originalArticle.indexOf(jpg);
            if (p < 0 || p == 0) {
                break;
            }
            int start = originalArticle.indexOf("http://");
            String url = originalArticle.substring(start, p + jpg.length());
            picUrls.add(url);
            sb.append(originalArticle.substring(0, start) + "(图" + picSize + ")");

            originalArticle = originalArticle.substring(p + 4);
            picSize++;
        }
        Log.e(TAG, "convert: picSize " + picSize);

        Log.e(TAG, "convert: picUrls  " + picUrls.toString());
        Log.e(TAG, "convert: article " + sb.toString());

        model.setArticle(sb.toString());
        model.setPicUrls(picUrls);
        return model;
    }
}
