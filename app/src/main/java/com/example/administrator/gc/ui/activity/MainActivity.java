package com.example.administrator.gc.ui.activity;

import android.content.Intent;

import com.android.debug.hv.ViewServer;
import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.ui.fragment.AttentionFragment;
import com.example.administrator.gc.ui.fragment.ForumFragment;
import com.example.administrator.gc.ui.fragment.IndexHomeFragment;
import com.example.administrator.gc.ui.fragment.MineFragment;
import com.example.administrator.gc.utils.FragmentUtils;
import com.example.administrator.gc.utils.ToastUtils;
import com.example.administrator.gc.widget.BottomNav;

public class MainActivity extends BaseActivity {

    private BottomNav bottomNav;
    private BaseFragment[] fragments = new BaseFragment[4];

    @Override
    protected void initView() {
        if (!cache.readBooleanValue("hasRun", false)) {
            startActivity(new Intent(this, GuideActivity.class));
        }
        ViewServer.get(this).addWindow(this);
        setContentView(R.layout.activity_main);
        bottomNav = (BottomNav) findViewById(R.id.mainBottomNav);
        switchFragment(0);
    }

    @Override
    protected void setListener() {
        bottomNav.setListener(onNavItemClickListener);
        getToolbar().setNavigationOnClickListener(null);
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
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void bind() {
    }

    @Override
    protected void unBind() {
        ViewServer.get(this).removeWindow(this);
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
}
