package com.example.administrator.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.presenter.activity.PostDetailPresenter;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PostDetailActivity extends BaseActivity {

    PostDetailPresenter presenter;
    private String urls;

    private TextView show;

    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, PostDetailActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_post_detail);

        show = (TextView) findViewById(R.id.show);
        show.setMovementMethod(ScrollingMovementMethod.getInstance());
        Intent intent = getIntent();
        urls = intent.getStringExtra("urls");
    }


    public void setShow(String s) {
        show.setText(s);

    }


    @Override
    protected void setListener() {
/*
        view.loadUrl(Urls.BASE_URL + "/" + urls);
        WebSettings webSettings = view.getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        view.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
*/

    }

    @Override
    protected void bind() {
        presenter = new PostDetailPresenter();
        presenter.bind(this);
        presenter.getData(urls);
    }

    @Override
    protected void unBind() {
        presenter.unBind();
    }
}
