package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/4/11.
 */
public class UserMessageModel extends BaseModel {

    private String userName;
    private String comment;
    private String userType;
    private String userPhotoSrc;
    private String userHomePageUrl;
    private String date;

    @Override
    public String toString() {
        return "UserMessageModel{" +
                "userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                ", userType='" + userType + '\'' +
                ", userPhotoSrc='" + userPhotoSrc + '\'' +
                ", userHomePageUrl='" + userHomePageUrl + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPhotoSrc() {
        return userPhotoSrc;
    }

    public void setUserPhotoSrc(String userPhotoSrc) {
        this.userPhotoSrc = userPhotoSrc;
    }

    public String getUserHomePageUrl() {
        return userHomePageUrl;
    }

    public void setUserHomePageUrl(String userHomePageUrl) {
        this.userHomePageUrl = userHomePageUrl;
    }
}
