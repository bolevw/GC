package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

import java.util.List;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokeItemModel extends BaseModel {
    private List<JokeModel> data;

    public List<JokeModel> getData() {
        return data;
    }

    public void setData(List<JokeModel> data) {
        this.data = data;
    }
}
