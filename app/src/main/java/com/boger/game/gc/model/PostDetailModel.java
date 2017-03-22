package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/4/11.
 */
public class PostDetailModel extends BaseModel {
    private UserMessageModel userMessageModel;

    private String content;

    @Override
    public String toString() {
        return "PostDetailModel{" +
                "userMessageModel=" + userMessageModel +
                ", content='" + content + '\'' +
                '}';
    }

    public UserMessageModel getUserMessageModel() {
        return userMessageModel;
    }

    public void setUserMessageModel(UserMessageModel userMessageModel) {
        this.userMessageModel = userMessageModel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
