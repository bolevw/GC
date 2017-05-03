package com.boger.game.gc.ui.fragment;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.boger.game.gc.R;
import com.boger.game.gc.api.Urls;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.presenter.fragment.WebPresenter;
import com.boger.game.gc.ui.activity.MainActivity;

import butterknife.BindView;

public class WebFragment extends BaseFragment {
    private WebPresenter presenter;
    @BindView(R.id.container)
    FrameLayout container;
    private WebView webView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initViewData() {
        webView = new WebView(getActivity());
        container.addView(webView);

        webView.loadUrl(Urls.INDEX_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
    }

    @Override
    protected void bind() {
        this.presenter = new WebPresenter();
        this.presenter.bind(this);
        this.presenter.getData();

    }

    @Override
    protected void setListener() {
        ((MainActivity) (getActivity())).setOnBackPressListener(new MainActivity.OnBackPressListener() {
            @Override
            public boolean onBack() {
                if (webView.canGoBack()) {
                    webView.goBack();
                    return false;
                } else {
                    return true;
                }
            }
        });
    }

    @Override
    protected void unbind() {
        this.presenter.unBind();
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            container.removeView(webView);
            webView.removeAllViews();
            webView.clearCache(true);
            webView.freeMemory();
            webView.pauseTimers();
            webView = null;
        }
        super.onDestroy();
    }
}
