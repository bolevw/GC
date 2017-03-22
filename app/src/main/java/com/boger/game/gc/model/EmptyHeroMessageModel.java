package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by liubo on 16/7/22.
 */

public class EmptyHeroMessageModel extends BaseModel {
    private  int status;
    private String info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
