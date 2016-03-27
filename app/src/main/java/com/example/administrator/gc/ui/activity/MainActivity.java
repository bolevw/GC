package com.example.administrator.gc.ui.activity;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.ui.fragment.AttentionFragment;
import com.example.administrator.gc.ui.fragment.ForumFragment;
import com.example.administrator.gc.ui.fragment.IndexHomeFragment;
import com.example.administrator.gc.ui.fragment.MineFragment;
import com.example.administrator.gc.utils.FragmentUtils;
import com.example.administrator.gc.widget.BottomNav;

public class MainActivity extends BaseActivity {

    private BottomNav bottomNav;
    BaseFragment[] fragments = new BaseFragment[4];


    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        bottomNav = (BottomNav) findViewById(R.id.mainBottomNav);
        switchFragment(0);

    }

    @Override
    protected void setListener() {
        bottomNav.setListener(onNavItemClickListener);
    }

    BottomNav.OnNavItemClickListener onNavItemClickListener = new BottomNav.OnNavItemClickListener() {
        @Override
        public void onItemClick(int position) {
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

        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.containerFrameLayout, fragments[p], false, fragment.TAG);
    }

    @Override
    protected void bind() {

    }

    @Override
    protected void unBind() {

    }
}
