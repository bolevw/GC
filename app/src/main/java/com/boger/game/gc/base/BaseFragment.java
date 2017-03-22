package com.boger.game.gc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.boger.game.gc.R;
import com.boger.game.gc.cache.Cache;
import com.boger.game.gc.widget.LoadingView;

/**
 * Created by Administrator on 2016/3/21.
 */
public abstract class BaseFragment extends Fragment {

    public static  String TAG = null;

    private LoadingView loadingView;
    private BaseActivity activity;
    protected Cache cache = Cache.getInstance(BaseApplication.getContext());

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
        TAG = this.getClass().getSimpleName();
        startLoading();
        initView(view);
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
        this.activity = (BaseActivity) context;
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

    protected BaseActivity getBaseActivity() {
        return this.activity;
    }

    protected void hideSoftKeyboard() {
        final InputMethodManager inputMethodManager = (InputMethodManager) getBaseActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getBaseActivity().getCurrentFocus();
        if (null == view) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected abstract void initView(View v);

    protected abstract void bind();

    protected abstract void setListener();

    protected abstract void unbind();

    @Override
    public void onDestroyView() {
        unbind();
        super.onDestroyView();
    }
}
