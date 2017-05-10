package com.boger.game.gc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.boger.game.gc.R;
import com.boger.game.gc.cache.Cache;
import com.boger.game.gc.widget.LoadingFailView;
import com.boger.game.gc.widget.LoadingView;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liubo on 2017/4/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static String TAG;

    private Toolbar toolbar;
    private LoadingView loadingView;
    private LoadingFailView failView;
    public Cache cache;
    protected Context mContext;
    private Unbinder unbinder;
    private OnDestroyListener onDestroyListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
        unbinder = ButterKnife.bind(this);
        TAG = this.getClass().getSimpleName();
        BaseApplication.setContext(getApplicationContext());
        cache = Cache.getInstance(this);
        mContext = this;

        initViewData();
        setListener();
        bind();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
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
            loadingView.stopAnim();
            loadingView.setVisibility(View.GONE);
        }

        if (null != failView) {
            failView.setVisibility(View.GONE);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    protected void hideSoftKeyboard() {
        final InputMethodManager inputMethod = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        final View view = getCurrentFocus();
        if (null == view) {
            return;
        }
        inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected abstract int getLayoutResId();

    protected abstract void initViewData();

    protected abstract void setListener();

    protected abstract void bind();

    @Override
    protected void onDestroy() {
        if (onDestroyListener != null) {
            onDestroyListener.onDestroy();
        }
        unbinder.unbind();
        super.onDestroy();
    }

    public void setOnDestroyListener(OnDestroyListener onDestroyListener) {
        this.onDestroyListener = onDestroyListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
