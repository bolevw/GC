package com.example.administrator.gc.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.administrator.gc.R;
import com.example.administrator.gc.widget.LoadingFailView;
import com.example.administrator.gc.widget.LoadingView;

/**
 * Created by Administrator on 2016/3/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LoadingView loadingView;
    private LoadingFailView failView;

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
        loadingView = (LoadingView) findViewById(R.id.loadingView);
        failView = (LoadingFailView) findViewById(R.id.loadingFailView);
    }

    public void startLoading() {
        if (null != loadingView) {
            loadingView.start();
            if (failView != null && failView.getVisibility() == View.VISIBLE) {
                failView.setVisibility(View.GONE);
            }
        }
    }

    public void logError(Throwable e) {
        Log.e("error", e.toString());
    }

    public void stopLoading() {
        if (null != loadingView) {
            loadingView.setVisibility(View.GONE);
        }

        if (null != failView) {
            failView.setVisibility(View.GONE);
        }
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
