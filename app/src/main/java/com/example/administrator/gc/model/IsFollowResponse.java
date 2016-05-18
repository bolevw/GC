package com.example.administrator.gc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liubo on 2016/5/18.
 */
public class IsFollowResponse  {
    @SerializedName("results")
    @Expose
    private List<Object> results;

    public List<Object> getResults() {
        return results;
    }

    public void setResults(List<Object> results) {
        this.results = results;
    }
}
