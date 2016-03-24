package com.example.administrator.gc.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class PreviewGroup {

    private String name;
    private String urls;
    private String imageSrc;
    private List<GroupItemModel> groupItemList;


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

class GroupItemModel {
    private String name;
    private String imageSrc;

    private String url;

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
