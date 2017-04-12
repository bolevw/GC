package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ForumModel extends BaseModel {

    private String imageSrc;
    private String forumName;
    private String forumCount;
    private String urls;
    private List<ForumItemModel> forumItems;


    @Override
    public String toString() {
        return "ForumModel{" +
                "imageSrc='" + imageSrc + '\'' +
                ", forumName='" + forumName + '\'' +
                ", forumCount='" + forumCount + '\'' +
                ", urls='" + urls + '\'' +
                ", forumItems=" + forumItems +
                '}';
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

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getForumCount() {
        return forumCount;
    }

    public void setForumCount(String forumCount) {
        this.forumCount = forumCount;
    }

    public List<ForumItemModel> getForumItems() {
        return forumItems;
    }

    public void setForumItems(List<ForumItemModel> forumItems) {
        this.forumItems = forumItems;
    }


}


