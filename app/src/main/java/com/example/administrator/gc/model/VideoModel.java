package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/4/26.
 */
public class VideoModel extends BaseModel{
    private String imgSrc;
    private String time;
    private String nums;
    private String url;
    private String title;


    @Override
    public String toString() {
        return "VideoModel{" +
                "imgSrc='" + imgSrc + '\'' +
                ", time='" + time + '\'' +
                ", nums='" + nums + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public VideoModel() {
    }

    public VideoModel(String imgSrc, String time, String nums, String url, String title) {
        this.imgSrc = imgSrc;
        this.time = time;
        this.nums = nums;
        this.url = url;
        this.title = title;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
