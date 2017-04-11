package com.boger.game.gc.ui.activity;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/4/6.
 */
public class AboutActivity extends BaseSwipeBackActivity {

    @Override
    protected void initViewData() {
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

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }
}
