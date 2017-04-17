package com.boger.game.gc.model;

/**
 * Created by liubo on 2017/4/17.
 */

public class ForumIndexHeaderModel {
    private String title;
    private String iconUrls;
    private String themeCounts = "0";
    private String todayCounts = "0";

    public ForumIndexHeaderModel(String title, String iconUrls, String themeCounts, String todayCounts) {
        this.title = title;
        this.iconUrls = iconUrls;
        this.themeCounts = themeCounts;
        this.todayCounts = todayCounts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrls() {
        return iconUrls;
    }

    public void setIconUrls(String iconUrls) {
        this.iconUrls = iconUrls;
    }

    public String getThemeCounts() {
        return themeCounts;
    }

    public void setThemeCounts(String themeCounts) {
        this.themeCounts = themeCounts;
    }

    public String getTodayCounts() {
        return todayCounts;
    }

    public void setTodayCounts(String todayCounts) {
        this.todayCounts = todayCounts;
    }
}

