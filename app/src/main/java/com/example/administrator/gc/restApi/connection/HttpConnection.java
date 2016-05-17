package com.example.administrator.gc.restApi.connection;

import com.example.administrator.gc.restApi.client.OkClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liubo on 2016/5/17.
 */
public class HttpConnection<T> {
    private Retrofit retrofit;

    public static class Builder {
        private String urls;

        public Builder(String urls) {
            this.urls = urls;
        }

        public HttpConnection build() {
            return new HttpConnection(this);
        }
    }

    private HttpConnection(Builder b) {
        retrofit = new Retrofit.Builder()
                .client(OkClient.getInstance())
                .baseUrl(b.urls)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getConnection() {
        return retrofit;
    }

}
