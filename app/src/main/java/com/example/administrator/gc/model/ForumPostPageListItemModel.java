package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumPostPageListItemModel extends BaseModel {
    private ArrayList<ForumPostListItemModel> list;

    private String nextPageUrls;

    @Override
    public String toString() {
        return "ForumPostPageListItemModel{" +
                "list=" + list +
                ", nextPageUrls='" + nextPageUrls + '\'' +
                '}';
    }

    public ArrayList<ForumPostListItemModel> getList() {
        return list;
    }

    public void setList(ArrayList<ForumPostListItemModel> list) {
        this.list = list;
    }

    public String getNextPageUrls() {
        return nextPageUrls;
    }

    public void setNextPageUrls(String nextPageUrls) {
        this.nextPageUrls = nextPageUrls;
    }
}
