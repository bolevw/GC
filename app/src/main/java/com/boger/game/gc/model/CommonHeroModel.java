package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

import java.util.List;

/**
 * Created by liubo on 2016/6/2.
 */
public class CommonHeroModel extends BaseModel {
    private String message;
    private List<HeroModel> herostr;

    @Override
    public String toString() {
        return "CommonHeroModel{" +
                "message='" + message + '\'' +
                ", herostr=" + herostr +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HeroModel> getHerostr() {
        return herostr;
    }

    public void setHerostr(List<HeroModel> herostr) {
        this.herostr = herostr;
    }
}
