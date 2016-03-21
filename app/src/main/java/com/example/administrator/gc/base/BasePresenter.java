package com.example.administrator.gc.base;

/**
 * Created by Administrator on 2016/3/21.
 */
public interface BasePresenter<T> {

    void bind(T view);
    void unBind();
}
