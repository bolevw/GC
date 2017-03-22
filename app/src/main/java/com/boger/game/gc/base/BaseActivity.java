package com.boger.game.gc.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.boger.game.gc.R;
import com.boger.game.gc.cache.Cache;
import com.boger.game.gc.widget.LoadingFailView;
import com.boger.game.gc.widget.LoadingView;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/3/21.
 */
public abstract class BaseActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener {
    public static String TAG;

    private Toolbar toolbar;
    private LoadingView loadingView;
    private LoadingFailView failView;
    public Cache cache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniSwipeBack();
        TAG = this.getClass().getSimpleName();
        BaseApplication.setContext(getApplicationContext());
        cache = Cache.getInstance(this);

        initView();
        setListener();
        bind();
        /*  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
    }

    /**
     * 滑动关闭页面
     */
    private void iniSwipeBack() {
        int height = getWindowManager().getDefaultDisplay().getHeight();
        if (isSupportSwipeBack()) {
            SlidingPaneLayout layout = new SlidingPaneLayout(this);
            try {
                Field field = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                field.setAccessible(true);
                field.set(layout, 0);
            } catch (Exception e) {
                logError(e);
                e.printStackTrace();
            }

            layout.setPanelSlideListener(this);
            layout.setSliderFadeColor(ContextCompat.getColor(this, android.R.color.transparent));

            FrameLayout leftView = new FrameLayout(this);
            leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(leftView, 0);
            leftView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));

            final ViewGroup diView = (ViewGroup) getWindow().getDecorView();
            ViewGroup childView = (ViewGroup) diView.getChildAt(0);
            childView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
            diView.removeAllViews();
            diView.addView(layout);
            childView.setLayoutParams(new SlidingPaneLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            layout.addView(childView, 1);
        }
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onPanelClosed(View view) {

    }

    @Override
    public void onPanelOpened(View view) {
        finish();
    }

    @Override
    public void onPanelSlide(View view, float v) {
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

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
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
