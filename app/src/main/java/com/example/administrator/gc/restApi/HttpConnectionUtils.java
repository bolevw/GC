package com.example.administrator.gc.restApi;

import com.example.administrator.gc.restApi.connection.HttpConnection;

/**
 * Created by liubo on 2016/5/17.
 */
public class HttpConnectionUtils {

    private static HttpConnectionUtils instance;

    private HttpConnection connection;

    static class Builder {
        private String baseUrl;

        public Builder(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public HttpConnectionUtils build() {
            return new HttpConnectionUtils(this);
        }
    }

    private HttpConnectionUtils(Builder b) {
        connection = new HttpConnection.Builder(b.baseUrl).build();
    }

    public HttpConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpConnection connection) {
        this.connection = connection;
    }
}
