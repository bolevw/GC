package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by liubo on 2016/4/7.
 */
public class ArticleCoverModel extends BaseModel {

    private String name;
    private String authName;
    private String date;
    private String imageSrc;
    private String urls;
    private String commentCount;


    @Override
    public String toString() {
        return "ForumPostListItemModel{" +
                "name='" + name + '\'' +
                ", authName='" + authName + '\'' +
                ", date='" + date + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                ", urls='" + urls + '\'' +
                ", commentCount='" + commentCount + '\'' +
                '}';
    }

    public ArticleCoverModel() {
    }

    public ArticleCoverModel(String name, String authName, String date, String imageSrc, String urls, String commentCount) {
        this.name = name;
        this.authName = authName;
        this.date = date;
        this.imageSrc = imageSrc;
        this.urls = urls;
        this.commentCount = commentCount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
