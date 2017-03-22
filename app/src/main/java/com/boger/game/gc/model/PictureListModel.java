package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

import java.util.List;

/**
 * Created by liubo on 2016/6/3.
 */
public class PictureListModel extends BaseModel {
    private List<PictureModel> results;

    public List<PictureModel> getResults() {
        return results;
    }

    public void setResults(List<PictureModel> results) {
        this.results = results;
    }
}
