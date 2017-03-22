package com.boger.game.gc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.ui.fragment.childfragment.AttentionPersonFragment;
import com.boger.game.gc.ui.fragment.childfragment.AttentionPostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/22.
 */
public class AttentionFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;


    private String[] titles = new String[]{"关注的帖子", "关注的人"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attention, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initView(View v) {

    }

    @Override
    protected void bind() {

    }

    @Override
    protected void setListener() {
        viewPager.setAdapter(new VPAdapter(getChildFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void unbind() {

    }

    private class VPAdapter extends FragmentPagerAdapter {

        public VPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new AttentionPostFragment();
            } else {
                return new AttentionPersonFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
