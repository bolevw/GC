package com.example.administrator.gc.ui.activity;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.ui.fragment.CommonHeroFragment;
import com.example.administrator.gc.ui.fragment.ForecastFragment;
import com.example.administrator.gc.ui.fragment.LevelFragment;
import com.example.administrator.gc.ui.fragment.RecordFragment;
import com.example.administrator.gc.ui.fragment.SearchUserInfoFragment;
import com.example.administrator.gc.ui.fragment.childfragment.RecommendCFragment;
import com.example.administrator.gc.utils.FragmentUtils;

/**
 * Created by liubo on 2016/5/24.
 */
public class LoLActivity extends BaseActivity {


    @Override
    protected void initView() {
        setContentView(R.layout.activity_lol);
        int action = getIntent().getIntExtra("lol_action", 1);
        switch (action) {
            case RecommendCFragment.LOL_SEARCH:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, SearchUserInfoFragment.newInstance(), false, SearchUserInfoFragment.class.getSimpleName());
                break;
            case RecommendCFragment.LOL_RECORD:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, RecordFragment.newInstance(), false, RecordFragment.class.getSimpleName());
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
}
