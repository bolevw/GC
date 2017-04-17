package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

import java.util.List;

/**
 * 论坛分区model
 * Created by Administrator on 2016/4/26.
 */
public class ForumIndexModel extends BaseModel {
    private ForumIndexHeaderModel headerModel;
    private List<VideoModel> videoList; //视频
    private List<ChildrenModuleCoverModel> list; //论坛分区分类
    private ArticleCoverListModel articleCoverListModel;

    public ForumIndexModel(ForumIndexHeaderModel headerModel, List<VideoModel> videoList, List<ChildrenModuleCoverModel> list, ArticleCoverListModel articleCoverListModel) {
        this.headerModel = headerModel;
        this.videoList = videoList;
        this.list = list;
        this.articleCoverListModel = articleCoverListModel;
    }

    public List<VideoModel> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoModel> videoList) {
        this.videoList = videoList;
    }

    public ForumIndexHeaderModel getHeaderModel() {
        return headerModel;
    }

    public void setHeaderModel(ForumIndexHeaderModel headerModel) {
        this.headerModel = headerModel;
    }

    public ArticleCoverListModel getArticleCoverListModel() {
        return articleCoverListModel;
    }

    public void setArticleCoverListModel(ArticleCoverListModel articleCoverListModel) {
        this.articleCoverListModel = articleCoverListModel;
    }

    public List<ChildrenModuleCoverModel> getList() {
        return list;
    }

    public void setList(List<ChildrenModuleCoverModel> list) {
        this.list = list;
    }
}
