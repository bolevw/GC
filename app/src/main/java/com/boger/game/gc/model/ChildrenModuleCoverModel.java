package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by liubo on 2016/4/7.
 */
public class ChildrenModuleCoverModel extends BaseModel {
    private String name;
    private String newCount;
    private String themeCount;
    private String postCount;
    private String urls;
    private String imageUrls;


    @Override
    public String toString() {
        return "ForumItemDetailModel{" +
                "name='" + name + '\'' +
                ", newCount='" + newCount + '\'' +
                ", themeCount='" + themeCount + '\'' +
                ", postCount='" + postCount + '\'' +
                ", urls='" + urls + '\'' +
                ", imageUrls='" + imageUrls + '\'' +
                '}';
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewCount() {
        return newCount;
    }

    public void setNewCount(String newCount) {
        this.newCount = newCount;
    }

    public String getThemeCount() {
        return themeCount;
    }

    public void setThemeCount(String themeCount) {
        this.themeCount = themeCount;
    }

    public String getPostCount() {
        return postCount;
    }

    public void setPostCount(String postCount) {
        this.postCount = postCount;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}
