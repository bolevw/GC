package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/4/14.
 */
public class PersonalHomePageModel extends BaseModel {
    private String bgSrc;
    private String avatarSrc;
    private String username;
    private String userLevel;

    private String money;
    private String prestige;
    private String grass;

    private String themeUrl;
    private String friendUrl;

    @Override
    public String toString() {
        return "PersonalHomePageModel{" +
                "bgSrc='" + bgSrc + '\'' +
                ", avatarSrc='" + avatarSrc + '\'' +
                ", username='" + username + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", money='" + money + '\'' +
                ", prestige='" + prestige + '\'' +
                ", grass='" + grass + '\'' +
                ", themeUrl='" + themeUrl + '\'' +
                ", friendUrl='" + friendUrl + '\'' +
                '}';
    }

    public String getBgSrc() {
        return bgSrc;
    }

    public void setBgSrc(String bgSrc) {
        this.bgSrc = bgSrc;
    }

    public String getAvatarSrc() {
        return avatarSrc;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPrestige() {
        return prestige;
    }

    public void setPrestige(String prestige) {
        this.prestige = prestige;
    }

    public String getGrass() {
        return grass;
    }

    public void setGrass(String grass) {
        this.grass = grass;
    }

    public String getThemeUrl() {
        return themeUrl;
    }

    public void setThemeUrl(String themeUrl) {
        this.themeUrl = themeUrl;
    }

    public String getFriendUrl() {
        return friendUrl;
    }

    public void setFriendUrl(String friendUrl) {
        this.friendUrl = friendUrl;
    }
}
