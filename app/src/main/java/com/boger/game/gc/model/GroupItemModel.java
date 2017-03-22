package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/3/25.
 */
public class GroupItemModel extends BaseModel{
    private String name;
    private String imageSrc;

    private String url;

    @Override
    public String toString() {
        return "GroupItemModel{" +
                "name='" + name + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public GroupItemModel() {
    }

    public GroupItemModel(String name, String imageSrc, String url) {
        this.name = name;
        this.imageSrc = imageSrc;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}