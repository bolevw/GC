package com.boger.game.gc.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseActivity;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.ui.fragment.WebFragment;
import com.boger.game.gc.ui.fragment.IndexHomeFragment;
import com.boger.game.gc.ui.fragment.MineFragment;
import com.boger.game.gc.ui.fragment.SquareFragment;
import com.boger.game.gc.utils.FragmentUtils;
import com.boger.game.gc.utils.ToastUtils;
import com.boger.game.gc.widget.bottomnav.BottomNavChild;
import com.boger.game.gc.widget.bottomnav.BottomNavController;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mainBottomNav)
    BottomNavController bottomNav;
    private BaseFragment[] fragments = new BaseFragment[4];

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViewData() {
        if (!cache.readBooleanValue("hasRun", false)) {
            startActivity(new Intent(this, GuideActivity.class));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        bottomNav
                .addItem(new BottomNavChild
                        .Builder(mContext)
                        .setTitle(R.string.attention)
                        .setImageRes(R.drawable.tab_attention)
                        .build())
                .addItem(new BottomNavChild
                        .Builder(mContext)
                        .setImageRes(R.drawable.tab_square)
                        .setTitle(R.string.square)
                        .build())
                .addItem(new BottomNavChild
                        .Builder(mContext)
                        .setImageRes(R.drawable.tab_mine)
                        .setTitle(R.string.mine)
                        .build())
                .addItem(new BottomNavChild
                        .Builder(mContext)
                        .setImageRes(R.drawable.tab_message)
                        .setTitle(R.string.message)
                        .build())
                .setSelectItem(1)
                .build(onNavItemClickListener);
    }

    @Override
    protected void setListener() {

    }

    private BottomNavController.OnChildClickListener onNavItemClickListener = new BottomNavController.OnChildClickListener() {
        @Override
        public void onClick(int position) {
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
                    fragment = new SquareFragment();
                    fragments[1] = fragment;
                    break;
                case 2:
                    fragment = new MineFragment();
                    fragments[2] = fragment;
                    break;
                case 3:
                    fragment = new WebFragment();
                    fragments[3] = fragment;
                    break;
            }
        }
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.containerFrameLayout, fragments[p], false, BaseFragment.TAG);
    }

    @Override
    protected void bind() {
    }

    private long times;

    @Override
    public void onBackPressed() {
        boolean result = true;
        if (onBackPressListener != null) {
            result = onBackPressListener.onBack();
        }
        if (result) {
            long secTime = times;
            times = System.currentTimeMillis();
            ToastUtils.showNormalToast("再一次退出程序");
            if (Math.abs(secTime - times) < 2000) {
                super.onBackPressed();
            }
        }

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

    OnBackPressListener onBackPressListener;

    public void setOnBackPressListener(OnBackPressListener onBackPressListener) {
        this.onBackPressListener = onBackPressListener;
    }

    public interface OnBackPressListener {
        boolean onBack();
    }
}
