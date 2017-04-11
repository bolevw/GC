package com.boger.game.gc.ui.activity;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.ui.fragment.CommonHeroFragment;
import com.boger.game.gc.ui.fragment.ForecastFragment;
import com.boger.game.gc.ui.fragment.HeroMessageFragment;
import com.boger.game.gc.ui.fragment.LevelFragment;
import com.boger.game.gc.ui.fragment.RecordFragment;
import com.boger.game.gc.ui.fragment.SearchUserInfoFragment;
import com.boger.game.gc.ui.fragment.childfragment.RecommendCFragment;
import com.boger.game.gc.utils.FragmentUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by liubo on 2016/5/24.
 */
public class LoLActivity extends BaseSwipeBackActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lol;
    }

    @Override
    protected void initViewData() {
        int action = getIntent().getIntExtra("lol_action", 1);
        switch (action) {
            case RecommendCFragment.LOL_SEARCH:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, SearchUserInfoFragment.newInstance(), false, SearchUserInfoFragment.class.getSimpleName());
                break;
            case RecommendCFragment.LOL_RECORD:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, RecordFragment.newInstance(), false, RecordFragment.class.getSimpleName());
                break;
            case RecommendCFragment.LOL_HERO_MESSAGE:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, HeroMessageFragment.newInstance(), false, HeroMessageFragment.class.getSimpleName());
                break;
            case RecommendCFragment.LOL_COMMON_HERO:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, CommonHeroFragment.newInstance(), false, CommonHeroFragment.class.getSimpleName());
                break;
            case RecommendCFragment.LOL_LEVEL:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, LevelFragment.newInstance(), false, LevelFragment.class.getSimpleName());
                break;
            case RecommendCFragment.LOL_FORECAST:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, ForecastFragment.newInstance(), false, ForecastFragment.class.getSimpleName());
                break;
            default:
                break;

        }
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
