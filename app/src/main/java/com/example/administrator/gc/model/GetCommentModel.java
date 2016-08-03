package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

import java.util.List;

/**
 * Created by liubo on 16/8/2.
 */

public class GetCommentModel extends BaseModel {
    private List<CommentModel> results;


    public List<CommentModel> getResults() {
        return results;
    }

    public void setResults(List<CommentModel> results) {
        this.results = results;
    }
}
