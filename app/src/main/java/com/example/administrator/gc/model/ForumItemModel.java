package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ForumItemModel extends BaseModel {
    private String name;
    private String urls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}