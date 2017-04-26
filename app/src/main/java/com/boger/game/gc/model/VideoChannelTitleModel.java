package com.boger.game.gc.model;

/**
 * Created by liubo on 2017/4/25.
 */

public class VideoChannelTitleModel {
    private String title;
    private String href;

    public VideoChannelTitleModel(String title, String href) {
        this.title = title;
        this.href = href;
    }

    @Override
    public String toString() {
        return "VideoChannelTitleModel{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}