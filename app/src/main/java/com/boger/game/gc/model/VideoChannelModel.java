package com.boger.game.gc.model;

/**
 * Created by liubo on 2017/4/25.
 */

public class VideoChannelModel {
    private String href;
    private String author;
    private String num;
    private String cover;
    private String time;
    private String name;

    public VideoChannelModel(String href, String author, String num, String cover, String time, String name) {
        this.href = href;
        this.author = author;
        this.num = num;
        this.cover = cover;
        this.time = time;
        this.name = name;
    }

    @Override
    public String toString() {
        return "VideoChannelModel{" +
                "href='" + href + '\'' +
                ", author='" + author + '\'' +
                ", num='" + num + '\'' +
                ", cover='" + cover + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
