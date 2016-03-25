package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class IndexModel extends BaseModel {

    private List<BannerModel> bannerList;
    private List<HotRankingModel> hotRankingList;
    private List<PreviewGroup> previewGroupList;


    @Override
    public String toString() {
        return "IndexModel{" +
                "bannerList=" + bannerList +
                ", hotRankingList=" + hotRankingList +
                ", previewGroupList=" + previewGroupList +
                '}';
    }

    public List<BannerModel> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerModel> bannerList) {
        this.bannerList = bannerList;
    }

    public List<HotRankingModel> getHotRankingList() {
        return hotRankingList;
    }

    public void setHotRankingList(List<HotRankingModel> hotRankingList) {
        this.hotRankingList = hotRankingList;
    }

    public List<PreviewGroup> getPreviewGroupList() {
        return previewGroupList;
    }

    public void setPreviewGroupList(List<PreviewGroup> previewGroupList) {
        this.previewGroupList = previewGroupList;
    }
}
