package com.boger.game.gc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.boger.game.gc.R;
import com.boger.game.gc.cache.Cache;
import com.boger.game.gc.widget.LoadingView;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liubo on 2016/3/21.
 */
public abstract class BaseFragment extends Fragment {
    public String TAG = getClass().getSimpleName();

    private LoadingView loadingView;
    private AppCompatActivity activity;
    protected Cache cache = Cache.getInstance(BaseApplication.getContext());
    private Unbinder unbinder;
    private OnDestroyListener onDestroyListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
//        TAG = this.getClass().getSimpleName();
        startLoading();
        initViewData();
        setListener();
        bind();
    }

    public void startLoading() {
        if (null != loadingView) {
            loadingView.start();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        this.activity = null;
        super.onDetach();
    }

    public void stopLoading() {
        if (null != loadingView) {
            loadingView.stopAnim();
            loadingView.setVisibility(View.GONE);
        }
    }

    public void logError(Throwable e) {
        Log.e("error", e.toString());
    }

    protected AppCompatActivity getBaseActivity() {
        return activity;
    }

    protected void hideSoftKeyboard() {
        final InputMethodManager inputMethodManager = (InputMethodManager) getBaseActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getBaseActivity().getCurrentFocus();
        if (null == view) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected abstract int getLayoutResId();

    protected abstract void initViewData();

    protected abstract void bind();

    protected abstract void setListener();

    @Override
    public void onDestroy() {
        if (onDestroyListener != null) {
            onDestroyListener.onDestroy();
        }
        hideSoftKeyboard();
        unbinder.unbind();
        super.onDestroy();
    }

    public void setOnDestroyListener(OnDestroyListener onDestroyListener) {
        this.onDestroyListener = onDestroyListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        hideSoftKeyboard();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}
