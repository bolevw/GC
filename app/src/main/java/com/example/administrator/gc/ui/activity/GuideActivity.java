package com.example.administrator.gc.ui.activity;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.widget.VPIndicator;

/**
 * Created by Administrator on 2016/5/4.
 */
public class GuideActivity extends BaseActivity {
    private Button skipButton;
    private ViewPager mGuideViewPager;
    private VPIndicator mGuideVp;

    private int[] colors = new int[]{Color.CYAN, Color.BLUE, Color.GREEN};
    private int[] imageViews = new int[]{R.mipmap.ic_about, R.mipmap.ic_action_back, R.mipmap.ic_friend};

    @Override
    protected void initView() {
        setContentView(R.layout.activity_guide);
        skipButton = (Button) findViewById(R.id.skipButton);
        mGuideViewPager = (ViewPager) findViewById(R.id.guideViewpager);
        mGuideVp = (VPIndicator) findViewById(R.id.guideVp);
        mGuideVp.setCount(colors.length);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
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
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colors[position], colors[position == 2 ? position : position + 1]);
                mGuideViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                mGuideVp.selection(position);
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
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
    protected void unBind() {

    }
}
