package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liubo on 2016/5/27.
 */
public class PlayerInfoModel extends BaseModel{
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("portrait")
    @Expose
    private String portrait;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("zhandouli")
    @Expose
    private String zhandouli;
    @SerializedName("good")
    @Expose
    private String good;


    @Override
    public String toString() {
        return "用户详情：" +
                ", portrait='" + portrait + '\'' +
                ", level='" + level + '\'' +
                ", zhandouli='" + zhandouli + '\'' +
                ", good='" + good + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getZhandouli() {
        return zhandouli;
    }

    public void setZhandouli(String zhandouli) {
        this.zhandouli = zhandouli;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }
}
