package com.example.administrator.gc.ui.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.presenter.activity.LoginPresenter;
import com.example.administrator.gc.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginActivity extends BaseActivity {


    LoginPresenter presenter;

    EditText accountEditText;
    Button loginButton;

    private GridView gridView;

    private List<String> list = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        accountEditText = (EditText) findViewById(R.id.accountEditText);

        loginButton = (Button) findViewById(R.id.loginButton);

        gridView = (GridView) findViewById(R.id.gridView);
    }

    @Override
    protected void setListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("url", accountEditText.getText().toString());
            }
        });

        for (int i = 0; i < 20; i++) {
            list.add("string " + i);
        }
        gridView.setAdapter(new GridAdapter());
    }

    @Override
    protected void bind() {
        this.presenter = new LoginPresenter();
        this.presenter.bind(this);
        this.presenter.getData();
    }

    @Override
    protected void unBind() {
        this.presenter.unBind();
    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.item_video, parent, false);
            return view;
        }
    }
}
