package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class GameModel extends BaseModel {
    private List<GameItemModel> list;


    @Override
    public String toString() {
        return "GameModel{" +
                "list=" + list +
                '}';
    }

    public List<GameItemModel> getList() {
        return list;
    }

    public void setList(List<GameItemModel> list) {
        this.list = list;
    }
}
