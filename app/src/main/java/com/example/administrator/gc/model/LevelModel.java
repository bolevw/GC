package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by liubo on 2016/6/2.
 */
public class LevelModel extends BaseModel {
    private String tier;
    private String rank;
    private String league_points;
    private String warzone_updated;

    @Override
    public String toString() {
        return "LevelModel{" +
                "tier='" + tier + '\'' +
                ", rank='" + rank + '\'' +
                ", league_points='" + league_points + '\'' +
                ", warzone_updated='" + warzone_updated + '\'' +
                '}';
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLeague_points() {
        return league_points;
    }

    public void setLeague_points(String league_points) {
        this.league_points = league_points;
    }

    public String getWarzone_updated() {
        return warzone_updated;
    }

    public void setWarzone_updated(String warzone_updated) {
        this.warzone_updated = warzone_updated;
    }
}
