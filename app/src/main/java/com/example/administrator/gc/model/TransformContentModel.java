package com.example.administrator.gc.model;

import java.util.List;

/**
 * Created by liubo on 2016/5/19.
 */
public class TransformContentModel {
    private String article;
    private List<String> picUrls;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }
}
