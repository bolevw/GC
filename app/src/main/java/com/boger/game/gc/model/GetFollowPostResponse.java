package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

import java.util.List;

/**
 * Created by liubo on 2016/6/3.
 */
public class GetFollowPostResponse extends BaseModel {
    private List<FollowPostModel> results;

    public List<FollowPostModel> getResults() {
        return results;
    }

    public void setResults(List<FollowPostModel> results) {
        this.results = results;
    }
}
