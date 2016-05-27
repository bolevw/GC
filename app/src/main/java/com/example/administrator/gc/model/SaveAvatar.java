package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liubo on 2016/5/27.
 */
public class SaveAvatar extends BaseModel {

    @SerializedName("url")
    @Expose
    private String url;

    public SaveAvatar(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
