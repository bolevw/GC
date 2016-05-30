package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by liubo on 2016/5/30.
 */
public class RecordModel extends BaseModel {
    private String message;
    private String Record;


    @Override
    public String toString() {
        return "RecordModel{" +
                "message='" + message + '\'' +
                ", Record='" + Record + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecord() {
        return Record;
    }

    public void setRecord(String record) {
        Record = record;
    }
}
