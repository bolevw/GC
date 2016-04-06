package com.example.administrator.gc.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.gc.R;

/**
 * Created by Administrator on 2016/3/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         BaseApplication.setContext(getApplicationContext());
        initView();
        setListener();
        bind();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract void bind();

    protected abstract void unBind();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind();
    }


}
