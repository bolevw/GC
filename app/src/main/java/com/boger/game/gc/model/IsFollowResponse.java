package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liubo on 2016/5/18.
 */
public class IsFollowResponse extends BaseModel {
    @SerializedName("results")
    @Expose
    private List<IsFollowBodyResponse> results;

    @Override
    public String toString() {
        return "IsFollowResponse{" +
                "results=" + results +
                '}';
    }

    public List<IsFollowBodyResponse> getResults() {
        return results;
    }

    public void setResults(List<IsFollowBodyResponse> results) {
        this.results = results;
    }
}
