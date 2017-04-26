package com.boger.game.gc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 2017/4/25.
 */

public class VideoIndexModel {
    private List<VideoBannerModel> banners = new ArrayList<>();
    private List<VideoIndexItemModel> items = new ArrayList<>();

    @Override
    public String toString() {
        return "VideoIndexModel{" +
                "banners=" + banners +
                ", items=" + items +
                '}';
    }

    public List<VideoBannerModel> getBanners() {
        return banners;
    }

    public void setBanners(List<VideoBannerModel> banners) {
        this.banners = banners;
    }

    public List<VideoIndexItemModel> getItems() {
        return items;
    }

    public void setItems(List<VideoIndexItemModel> items) {
        this.items = items;
    }
}
