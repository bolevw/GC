package com.example.administrator.gc.utils;

import android.util.Log;

import com.example.administrator.gc.model.TransformContentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 2016/5/19.
 */
public class ConvertArticleUtils {
    public static TransformContentModel convert(String originalArticle) {
        TransformContentModel model = new TransformContentModel();
        StringBuffer sb = new StringBuffer();
        List<String> picUrls = new ArrayList<>();
        boolean flag = true;
        int picSize = 0;
        while (flag) {
            int p = originalArticle.indexOf(".jpg");
            if (p < 0 || p == 0) {
                break;
            }
            int start = originalArticle.indexOf("http://");
            String url = originalArticle.substring(start, p + 4);
            picUrls.add(url);
            sb.append(originalArticle.substring(0, start) + "(图" + picSize + ")");

            originalArticle = originalArticle.substring(p + 4);
            picSize++;
        }
        Log.d("picSize", "picture size is : " + picSize);

        Log.d("picUrls", picUrls.toString());
        Log.d("article", sb.toString());

        model.setArticle(sb.toString());
        model.setPicUrls(picUrls);
        return model;
    }
}
