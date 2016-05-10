package com.example.administrator.gc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.administrator.gc.R;
import com.example.administrator.gc.widget.LoadingView;

/**
 * Created by Administrator on 2016/3/21.
 */
public abstract class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();
    private LoadingView loadingView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
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

    public void stopLoading() {
        if (null != loadingView) {
            loadingView.stopAnim();
            loadingView.setVisibility(View.GONE);
        }
    }


    public void logError(Throwable e) {
        Log.e("error", e.toString());
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }

    public void hideSoftKeyboard() {
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
        super.onDestroyView();
        unbind();
    }
}
