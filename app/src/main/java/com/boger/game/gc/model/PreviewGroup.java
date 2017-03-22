package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class PreviewGroup extends BaseModel{

    private String name;
    private String urls;
    private String imageSrc;
    private List<GroupItemModel> groupItemList;


    @Override
    public String toString() {
        return "PreviewGroup{" +
                "name='" + name + '\'' +
                ", urls='" + urls + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                ", groupItemList=" + groupItemList +
                '}';
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<GroupItemModel> getGroupItemList() {
        return groupItemList;
    }

    public void setGroupItemList(List<GroupItemModel> groupItemList) {
        this.groupItemList = groupItemList;
    }
}


