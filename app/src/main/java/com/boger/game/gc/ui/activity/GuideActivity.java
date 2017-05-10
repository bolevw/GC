package com.boger.game.gc.ui.activity;

import android.animation.ArgbEvaluator;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/5/4.
 */
public class GuideActivity extends BaseSwipeBackActivity {
    @BindView(R.id.guideBtmFl)
    FrameLayout guideBtmFl;

    private Button skipButton;
    private ViewPager mGuideViewPager;

    private int[] colors = new int[]{R.color.guide1, R.color.guide2, R.color.guide3};
    private int[] imageViews = new int[]{R.mipmap.guide1, R.mipmap.guide2, R.mipmap.ic_guide3};

    @Override
    protected void initViewData() {
        skipButton = (Button) findViewById(R.id.skipButton);
        mGuideViewPager = (ViewPager) findViewById(R.id.guideViewpager);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void setListener() {
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mGuideViewPager.setAdapter(new ViewPagerAp());
        final ArgbEvaluator evaluator = new ArgbEvaluator();
        mGuideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, ContextCompat.getColor(mContext, colors[position]), ContextCompat.getColor(mContext, colors[position == 2 ? position : position + 1]));
                guideBtmFl.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    skipButton.setText("Finish");
                } else {
                    skipButton.setText("skip");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ViewPagerAp extends PagerAdapter {

        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(GuideActivity.this);
            view.setImageResource(imageViews[position]);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(view);
            return view;
        }
    }

    @Override
    public void onBackPressed() {
        cache.saveBooleanValue("hasRun", true);
        super.onBackPressed();
    }

    @Override
    protected void bind() {

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
