package com.example.administrator.gc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liubo on 2016/5/18.
 */
public class IsFollowBodyResponse {
    @SerializedName("objectId")
    @Expose
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
