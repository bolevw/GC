package com.boger.game.gc.model;

import com.boger.game.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/4/11.
 */
public class PostDetailHeaderModel extends BaseModel {

    private String title;

    private UserMessageModel header;
    private String content;

    @Override
    public String toString() {
        return "PostDetailHeaderModel{" +
                "title='" + title + '\'' +
                ", header=" + header +
                ", content='" + content + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserMessageModel getHeader() {
        return header;
    }

    public void setHeader(UserMessageModel header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
