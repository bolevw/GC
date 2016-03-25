package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/3/24.
 */
public class BannerModel extends BaseModel {
    private String bannerImageSrc;
    private String bannerWebSrc;

    public String getBannerImageSrc() {
        return bannerImageSrc;
    }

    @Override
    public String toString() {
        return "BannerModel{" +
                "bannerImageSrc='" + bannerImageSrc + '\'' +
                ", bannerWebSrc='" + bannerWebSrc + '\'' +
                '}';
    }

    public void setBannerImageSrc(String bannerImageSrc) {
        this.bannerImageSrc = bannerImageSrc;
    }

    public String getBannerWebSrc() {
        return bannerWebSrc;
    }

    public void setBannerWebSrc(String bannerWebSrc) {
        this.bannerWebSrc = bannerWebSrc;
    }
}
