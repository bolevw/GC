package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/3/29.
 */
public class GameItemModel extends BaseModel{
    private String name;
    private String urls;
    private String imageSrc;


    @Override
    public String toString() {
        return "GameItemModel{" +
                "name='" + name + '\'' +
                ", urls='" + urls + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
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

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
