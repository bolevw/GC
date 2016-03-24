package com.example.administrator.gc.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class IndexModel {

    private List<BannerModel> bannerList;
    private List<HotRankingModel> hotRankingList;
    private List<PreviewGroup> previewGroupList;


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
