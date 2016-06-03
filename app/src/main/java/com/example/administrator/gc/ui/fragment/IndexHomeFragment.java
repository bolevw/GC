package com.example.administrator.gc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.ui.fragment.childfragment.GameCFragment;
import com.example.administrator.gc.ui.fragment.childfragment.JokeCFragment;
import com.example.administrator.gc.ui.fragment.childfragment.PictureCFragment;
import com.example.administrator.gc.ui.fragment.childfragment.RecommendCFragment;
import com.example.administrator.gc.ui.fragment.childfragment.RecreationCFragment;
import com.example.administrator.gc.utils.ToastUtils;

/**
 * Created by Administrator on 2016/3/22.
 */
public class IndexHomeFragment extends BaseFragment implements RecommendCFragment.ClickGetMoreListener {
    private ViewPager indexHomeViewPager;
    private TabLayout indexHomeTabLayout;

    private int[] viewPagerTitles = new int[]{R.string.recommend, R.string.game, R.string.joke, R.string.picture};
    private BaseFragment[] fragments = new BaseFragment[viewPagerTitles.length];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_index_home, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    protected void initView(View v) {
        indexHomeTabLayout = (TabLayout) v.findViewById(R.id.indexHomeTabLayout);
        indexHomeViewPager = (ViewPager) v.findViewById(R.id.indexHomeViewPager);

        getBaseActivity().getToolbar().setNavigationIcon(R.mipmap.ic_icon);
    }

    @Override
    protected void bind() {

    }

    @Override
    protected void setListener() {
        indexHomeViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        indexHomeTabLayout.setupWithViewPager(indexHomeViewPager);
        indexHomeViewPager.setOffscreenPageLimit(5);

    }

    @Override
    protected void unbind() {

    }

    @Override
    public void click() {
        indexHomeViewPager.setCurrentItem(1, true);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = fragments[position];
            if (fragment == null) {
                switch (position) {
                    case 0:
                        fragment = new RecommendCFragment();
                        fragments[0] = fragment;
                        ((RecommendCFragment) fragment).setGetMoreListener(IndexHomeFragment.this);
                        break;
                    case 1:
                        fragment = new GameCFragment();
                        fragments[1] = fragment;
                        break;
                    case 2:
                        fragment = new RecreationCFragment();
                        fragments[2] = fragment;
                        break;
                    case 3:
                        fragment = new JokeCFragment();
                        fragments[3] = fragment;
                        break;
                    case 4:
                        fragment = new PictureCFragment();
                        fragments[4] = fragment;
                        break;
                }
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return viewPagerTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(viewPagerTitles[position]);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_index_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            ToastUtils.showNormalToast("action search");
            return true;
        }
        if (id == R.id.action_history) {

        }
        if (id == R.id.action_scan) {

        }
        return super.onOptionsItemSelected(item);
    }
}
