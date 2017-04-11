package com.boger.game.gc.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.ui.fragment.AttentionFragment;
import com.boger.game.gc.ui.fragment.ForumFragment;
import com.boger.game.gc.ui.fragment.IndexHomeFragment;
import com.boger.game.gc.ui.fragment.MineFragment;
import com.boger.game.gc.utils.FragmentUtils;
import com.boger.game.gc.utils.ToastUtils;
import com.boger.game.gc.widget.BottomNav;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends BaseSwipeBackActivity {

    private BottomNav bottomNav;
    private BaseFragment[] fragments = new BaseFragment[4];

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViewData() {
//        if (!cache.readBooleanValue("hasRun", false)) {
            startActivity(new Intent(this, GuideActivity.class));
//        }
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cut_line));
        bottomNav = (BottomNav) findViewById(R.id.mainBottomNav);
        switchFragment(0);
    }

    @Override
    protected void setListener() {
        bottomNav.setListener(onNavItemClickListener);
    }

    private BottomNav.OnNavItemClickListener onNavItemClickListener = new BottomNav.OnNavItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if (position == 2) {
                if (!cache.readBooleanValue("isLogin", false)) {
                    position = 3;
                    ToastUtils.showNormalToast("请先登录！");
                    bottomNav.selectItem(position);
                }
            }
            switchFragment(position);
        }
    };

    private void switchFragment(int p) {
        BaseFragment fragment = fragments[p];
        if (fragment == null) {
            switch (p) {
                case 0:
                    fragment = new IndexHomeFragment();
                    fragments[0] = fragment;
                    break;
                case 1:
                    fragment = new ForumFragment();
                    fragments[1] = fragment;
                    break;
                case 2:
                    fragment = new AttentionFragment();
                    fragments[2] = fragment;
                    break;
                case 3:
                    fragment = new MineFragment();
                    fragments[3] = fragment;
                    break;
            }
        }
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.containerFrameLayout, fragments[p], false, BaseFragment.TAG);
    }

    @Override
    protected void bind() {
    }

    @Override
    protected void unBind() {
    }

    private long times;

    @Override
    public void onBackPressed() {
        long secTime = times;
        times = System.currentTimeMillis();
        ToastUtils.showNormalToast("再一次退出程序");
        if (Math.abs(secTime - times) < 2000) {
            super.onBackPressed();
        }
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
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
