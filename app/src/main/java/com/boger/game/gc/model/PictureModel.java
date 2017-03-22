package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by liubo on 2016/6/3.
 */
public class PictureModel extends BaseModel {
    public String who;
    public String url;
    public String type;
    public String desc;
    public boolean used;
    public String createdAt;
    public String updatedAt;
    public String publishedAt;

    @Override
    public String toString() {
        return "Meizi{" +
                "who='" + who + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", used=" + used +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
