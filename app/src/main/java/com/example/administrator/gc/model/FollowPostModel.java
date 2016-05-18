package com.example.administrator.gc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liubo on 5/18/16.
 */
public class FollowPostModel {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("postUrl")
    @Expose
    private String postUrl;
    @SerializedName("lzName")
    @Expose
    private String lzName;
    @SerializedName("followDate")
    @Expose
    private Long followDate;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getLzName() {
        return lzName;
    }

    public void setLzName(String lzName) {
        this.lzName = lzName;
    }

    public Long getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Long followDate) {
        this.followDate = followDate;
    }
}
