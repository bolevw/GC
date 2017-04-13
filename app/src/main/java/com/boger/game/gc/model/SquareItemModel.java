package com.boger.game.gc.model;

/**
 * Created by liubo on 2017/4/12.
 */

public class SquareItemModel {
    private String hrefUrl; //链接地址
    private String title; //标题
    private String imgSrc; //图标
    private String forumNum; //帖子总数


    @Override
    public String toString() {
        return "SquareItemModel{" +
                "hrefUrl='" + hrefUrl + '\'' +
                ", title='" + title + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", forumNum='" + forumNum + '\'' +
                '}';
    }

    public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getForumNum() {
        return forumNum;
    }

    public void setForumNum(String forumNum) {
        this.forumNum = forumNum;
    }
}
