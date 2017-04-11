package com.boger.game.gc.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.ui.fragment.childfragment.GameCFragment;
import com.boger.game.gc.ui.fragment.childfragment.JokeCFragment;
import com.boger.game.gc.ui.fragment.childfragment.PictureCFragment;
import com.boger.game.gc.ui.fragment.childfragment.RecommendCFragment;
import com.boger.game.gc.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/3/22.
 */
public class IndexHomeFragment extends BaseFragment implements RecommendCFragment.ClickGetMoreListener {
    @BindView(R.id.indexHomeViewPager)
    ViewPager indexHomeViewPager;
    @BindView(R.id.indexHomeTabLayout)
    TabLayout indexHomeTabLayout;

    private int[] viewPagerTitles = new int[]{R.string.recommend, R.string.game, R.string.joke, R.string.picture};
    private BaseFragment[] fragments = new BaseFragment[viewPagerTitles.length];


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_index_home;
    }

    @Override
    protected void initViewData() {
        setHasOptionsMenu(true);
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
                        fragment = new JokeCFragment();
                        fragments[2] = fragment;
                        break;
                    case 3:
                        fragment = new PictureCFragment();
                        fragments[3] = fragment;
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
