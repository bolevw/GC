package com.boger.game.gc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 2017/4/12.
 */

public class SquareListModel {
    List<SquareItemModel> list = new ArrayList<>();
    String title;

    @Override
    public String toString() {
        return "SquareListModel{" +
                "list=" + list +
                ", title='" + title + '\'' +
                '}';
    }

    public List<SquareItemModel> getList() {
        return list;
    }

    public void setList(List<SquareItemModel> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
