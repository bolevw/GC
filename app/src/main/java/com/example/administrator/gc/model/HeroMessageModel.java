package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

import java.util.List;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroMessageModel extends BaseModel {
    private String snFull;
    private Integer championsNum;
    private List<HeroDetailModel> content;

    @Override
    public String toString() {
        return "HeroMessageModel{" +
                "snFull='" + snFull + '\'' +
                ", championsNum=" + championsNum +
                ", content=" + content +
                '}';
    }

    public String getSnFull() {
        return snFull;
    }

    public void setSnFull(String snFull) {
        this.snFull = snFull;
    }

    public Integer getChampionsNum() {
        return championsNum;
    }

    public void setChampionsNum(Integer championsNum) {
        this.championsNum = championsNum;
    }

    public List<HeroDetailModel> getContent() {
        return content;
    }

    public void setContent(List<HeroDetailModel> content) {
        this.content = content;
    }
}
