package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokeModel extends BaseModel{
    private String content;

    private String hashId;

    private int unixtime;

    private String updatetime;

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setHashId(String hashId){
        this.hashId = hashId;
    }
    public String getHashId(){
        return this.hashId;
    }
    public void setUnixtime(int unixtime){
        this.unixtime = unixtime;
    }
    public int getUnixtime(){
        return this.unixtime;
    }
    public void setUpdatetime(String updatetime){
        this.updatetime = updatetime;
    }
    public String getUpdatetime(){
        return this.updatetime;
    }

}
