package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ArticleCoverListModel extends BaseModel {
    private ArrayList<ArticleCoverModel> list;

    private String nextPageUrls;

    @Override
    public String toString() {
        return "ForumPostPageListItemModel{" +
                "list=" + list +
                ", nextPageUrls='" + nextPageUrls + '\'' +
                '}';
    }

    public ArrayList<ArticleCoverModel> getList() {
        return list;
    }

    public void setList(ArrayList<ArticleCoverModel> list) {
        this.list = list;
    }

    public String getNextPageUrls() {
        return nextPageUrls;
    }

    public void setNextPageUrls(String nextPageUrls) {
        this.nextPageUrls = nextPageUrls;
    }
}
