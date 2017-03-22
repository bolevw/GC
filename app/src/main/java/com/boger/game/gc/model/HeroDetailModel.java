package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

import java.util.List;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroDetailModel extends BaseModel {
    private String championName;
    private String championNameCN;
    private Integer winRate;
    private Integer matchStat;
    private List<Integer> averageKDA;
    private String averageKDARating;
    private List<Integer> averageDamage;
    private List<Integer> averageEarn;
    private Integer averageMinionsKilled;
    private Integer totalMVP;
    private Integer totalHope;

    @Override
    public String toString() {
        return "HeroDetailModel{" +
                "championName='" + championName + '\'' +
                ", championNameCN='" + championNameCN + '\'' +
                ", winRate=" + winRate +
                ", matchStat=" + matchStat +
                ", averageKDA=" + averageKDA +
                ", averageKDARating='" + averageKDARating + '\'' +
                ", averageDamage=" + averageDamage +
                ", averageEarn=" + averageEarn +
                ", averageMinionsKilled=" + averageMinionsKilled +
                ", totalMVP=" + totalMVP +
                ", totalHope=" + totalHope +
                '}';
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getChampionNameCN() {
        return championNameCN;
    }

    public void setChampionNameCN(String championNameCN) {
        this.championNameCN = championNameCN;
    }

    public Integer getWinRate() {
        return winRate;
    }

    public void setWinRate(Integer winRate) {
        this.winRate = winRate;
    }

    public Integer getMatchStat() {
        return matchStat;
    }

    public void setMatchStat(Integer matchStat) {
        this.matchStat = matchStat;
    }

    public List<Integer> getAverageKDA() {
        return averageKDA;
    }

    public void setAverageKDA(List<Integer> averageKDA) {
        this.averageKDA = averageKDA;
    }

    public String getAverageKDARating() {
        return averageKDARating;
    }

    public void setAverageKDARating(String averageKDARating) {
        this.averageKDARating = averageKDARating;
    }

    public List<Integer> getAverageDamage() {
        return averageDamage;
    }

    public void setAverageDamage(List<Integer> averageDamage) {
        this.averageDamage = averageDamage;
    }

    public List<Integer> getAverageEarn() {
        return averageEarn;
    }

    public void setAverageEarn(List<Integer> averageEarn) {
        this.averageEarn = averageEarn;
    }

    public Integer getAverageMinionsKilled() {
        return averageMinionsKilled;
    }

    public void setAverageMinionsKilled(Integer averageMinionsKilled) {
        this.averageMinionsKilled = averageMinionsKilled;
    }

    public Integer getTotalMVP() {
        return totalMVP;
    }

    public void setTotalMVP(Integer totalMVP) {
        this.totalMVP = totalMVP;
    }

    public Integer getTotalHope() {
        return totalHope;
    }

    public void setTotalHope(Integer totalHope) {
        this.totalHope = totalHope;
    }
}
