package com.example.administrator.gc.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/3/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
        setListener();
        bind();
    }

    @Override
    protected void onDestroy() {
        unBind();
        super.onDestroy();
    }

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract void bind();

    protected abstract void unBind();


}
