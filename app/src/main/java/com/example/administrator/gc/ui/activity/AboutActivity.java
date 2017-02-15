package com.example.administrator.gc.ui.activity;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.utils.ToastUtils;
import com.example.administrator.gc.widget.NineGridVIewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class AboutActivity extends BaseActivity {
    NineGridVIewGroup nine;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_about);
        nine = (NineGridVIewGroup) findViewById(R.id.nine);
        List<String> urls = new ArrayList<>(9);
        int i = 0;
        while (i < 8) {
            urls.add(new String("https://oixgx79lw.qnssl.com/FuaI7iVBiu5cilAaMuKbqJEYRFEU"));
            i++;
        }
        nine.setViewData(urls);
        nine.setItemClick(new NineGridVIewGroup.onItemClick() {
            @Override
            public void onItemClick(String url) {
                ToastUtils.showNormalToast(url);
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
