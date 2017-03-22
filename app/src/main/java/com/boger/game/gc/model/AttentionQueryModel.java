package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by liubo on 2016/6/3.
 */
public class AttentionQueryModel extends BaseModel {
    private String userId;

    public AttentionQueryModel(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
