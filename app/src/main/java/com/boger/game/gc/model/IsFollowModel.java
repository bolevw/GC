package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liubo on 2016/5/18.
 */
public class IsFollowModel extends BaseModel {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("postUrl")
    @Expose
    private String postUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
}
