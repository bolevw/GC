package com.example.administrator.gc.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ForumDetailListActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_forum_detail_list);

        final EditText login = (EditText) findViewById(R.id.get);
        findViewById(R.id.gett).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ss", "s" + login.getText().toString());
            }
        });

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
