package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liubo on 2016/5/18.
 */
public class IsFollowBodyResponse extends BaseModel{
    @SerializedName("objectId")
    @Expose
    private String objectId;

    @Override
    public String toString() {
        return "IsFollowBodyResponse{" +
                "objectId='" + objectId + '\'' +
                '}';
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
