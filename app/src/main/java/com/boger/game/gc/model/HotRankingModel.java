package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/3/24.
 */
public class HotRankingModel extends BaseModel{
    private String imageSrc;
    private String name;
    private String urls;

    @Override
    public String toString() {
        return "HotRankingModel{" +
                "imageSrc='" + imageSrc + '\'' +
                ", name='" + name + '\'' +
                ", urls='" + urls + '\'' +
                '}';
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
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
