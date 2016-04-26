package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

import java.util.List;

/**
 * 论坛分区model
 * Created by Administrator on 2016/4/26.
 */
public class ForumPartitionModel extends BaseModel {
    private String title;
    private String imgSrc;
    private List<VideoModel> videoList; //视频
    private List<ForumItemDetailModel> list; //论坛分区分类


    @Override
    public String toString() {
        return "ForumPartitionModel{" +
                "title='" + title + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", videoList=" + videoList +
                ", list=" + list +
                '}';
    }

    public ForumPartitionModel() {
    }

    public ForumPartitionModel(String title, String imgSrc, List<VideoModel> videoList, List<ForumItemDetailModel> list) {
        this.title = title;
        this.imgSrc = imgSrc;
        this.videoList = videoList;
        this.list = list;
    }

    public List<VideoModel> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoModel> videoList) {
        this.videoList = videoList;
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

    public List<ForumItemDetailModel> getList() {
        return list;
    }

    public void setList(List<ForumItemDetailModel> list) {
        this.list = list;
    }
}
