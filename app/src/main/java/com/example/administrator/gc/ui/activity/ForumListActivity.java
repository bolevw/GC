package com.example.administrator.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;

import com.example.administrator.gc.base.BaseActivity;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumListActivity extends BaseActivity {

    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, ForumListActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void bind() {

    }

    @Override
    protected void unBind() {

    }
}
