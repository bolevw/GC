package com.boger.game.gc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 2017/4/25.
 */

public class VideoIndexItemModel {
    private VideoChannelTitleModel title;
    private List<VideoChannelModel> list = new ArrayList<>();

    @Override
    public String toString() {
        return "VideoIndexItemModel{" +
                "title=" + title +
                ", list=" + list +
                '}';
    }

    public VideoChannelTitleModel getTitle() {
        return title;
    }

    public void setTitle(VideoChannelTitleModel title) {
        this.title = title;
    }

    public List<VideoChannelModel> getList() {
        return list;
    }

    public void setList(List<VideoChannelModel> list) {
        this.list = list;
    }
}
