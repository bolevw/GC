package com.boger.game.gc.model;

/**
 * Created by liubo on 2017/4/25.
 */

public class VideoBannerModel {
    private String coverUrl;
    private String href;
    private String title;

    public VideoBannerModel() {
    }

    public VideoBannerModel(String coverUrl, String href, String title) {
        this.coverUrl = coverUrl;
        this.href = href;
        this.title = title;
    }

    @Override
    public String toString() {
        return "VideoBannerModel{" +
                "coverUrl='" + coverUrl + '\'' +
                ", href='" + href + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
