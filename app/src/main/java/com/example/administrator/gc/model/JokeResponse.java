package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

import java.util.List;

/**
 * Created by liubo on 2016/6/6.
 */
public class JokeResponse extends BaseModel{
        private int error_code;

        private String reason;

        private List<JokeModel> result;

        public void setError_code(int error_code){
            this.error_code = error_code;
        }
        public int getError_code(){
            return this.error_code;
        }
        public void setReason(String reason){
            this.reason = reason;
        }
        public String getReason(){
            return this.reason;
        }

    public List<JokeModel> getResult() {
        return result;
    }

    public void setResult(List<JokeModel> result) {
        this.result = result;
    }
}
