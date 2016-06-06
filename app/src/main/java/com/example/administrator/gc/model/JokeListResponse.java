package com.example.administrator.gc.model;

import java.util.List;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokeListResponse {
    private List<JokeModel> data;

    public void setData(List<JokeModel> data) {
        this.data = data;
    }

    public List<JokeModel> getData() {
        return this.data;
    }

}
