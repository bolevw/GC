package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by Administrator on 2016/4/11.
 */
public class PostDetailHeaderModel extends BaseModel {

    private String title;

    private UserMessageModel header;
    private String content;


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
