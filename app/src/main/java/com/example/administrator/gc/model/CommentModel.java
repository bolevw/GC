package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liubo on 16/8/1.
 */

public class CommentModel extends BaseModel {
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("admire")
    @Expose
    private Integer admire;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("objectId")
    @Expose
    private String objectId;


    public CommentModel(String time, String avatar, String name, Integer admire, String comment) {
        this.time = time;
        this.avatar = avatar;
        this.name = name;
        this.admire = admire;
        this.comment = comment;
    }

    public CommentModel() {
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAdmire() {
        return admire;
    }

    public void setAdmire(Integer admire) {
        this.admire = admire;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
