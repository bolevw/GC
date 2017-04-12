package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/4/5.
 */
class ForumItemModel extends BaseModel {
    private String name;
    private String urls;

    @Override
    public String toString() {
        return "ForumItemModel{" +
                "name='" + name + '\'' +
                ", urls='" + urls + '\'' +
                '}';
    }

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