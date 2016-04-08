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

    private TextView show;
    PostDetailPresenter presenter;
    private String urls;

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
