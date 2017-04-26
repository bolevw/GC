package com.boger.game.gc.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;

/**
 * Created by Administrator on 2016/3/22.
 */
public class VPIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {


    private Context context;

    private Animator animIn;
    private Animator animOut;
    private String[] titles;

    public VPIndicator(Context context) {
        super(context);
    }

    public VPIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        setPadding(dip2px(16), 0, dip2px(16), 0);
        setBackgroundResource(R.color.percent50Black);
        animIn = AnimatorInflater.loadAnimator(context, R.animator.vp_scale_with_alpha);
        animIn.setDuration(300);

        animOut = AnimatorInflater.loadAnimator(context, R.animator.vp_scale_with_alpha);
        animOut.setInterpolator(new RetrieveInterpolator());
        animOut.setDuration(300);
    }

    public void setCount(int count) {
        createIndicator(count);
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
        if (titles.length > 0) {
            TextView title = (TextView) getChildAt(getChildCount() - 1);
            title.setText(titles[0]);
        }
    }

    private void createIndicator(int count) {

        for (int i = 0; i < count; i++) {
            View view = new View(context);
            view.setBackgroundResource(R.drawable.white_circle);
            LayoutParams lp = new LayoutParams(dip2px(8), dip2px(8));
            lp.rightMargin = dip2px(6);
            out(view);
            addView(view, lp);
        }

        TextView title = new TextView(context);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        LayoutParams titleLp = new LayoutParams(dip2px(0), ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.weight = 1;
        addView(title, titleLp);

        View v = getChildAt(0);
        in(v);
    }

    private int currentPosition = 0;

    public void selection(int position) {
        if (position == currentPosition) {
            return;
        }

        View v = getChildAt(currentPosition);
        View v2 = getChildAt(position);
        if (titles != null && titles.length > 0) {
            TextView title = (TextView) getChildAt(getChildCount() - 1);
            title.setText(titles[position]);
        }
        currentPosition = position;
        out(v);
        in(v2);

    }

    private void in(View v) {
        if (animOut.isRunning())
            animOut.end();
        if (animIn.isRunning())
            animIn.end();

        animIn.setTarget(v);
        animIn.start();
    }

    private void out(View v) {
        if (animIn.isRunning())
            animIn.end();
        if (animOut.isRunning())
            animOut.end();

        animOut.setTarget(v);
        animOut.start();
    }

    private int dip2px(int value) {
        if (value < 0) {
            value = 5;
        }
        return (int) (getResources().getDisplayMetrics().density * value + 0.5f);
    }

    public void setViewPager(ViewPager viewPager) {
        setViewPager(viewPager, null);
    }

    public void setViewPager(ViewPager viewPager, String[] titles) {
        viewPager.addOnPageChangeListener(this);
        setCount(viewPager.getAdapter().getCount());
        if (titles != null) {
            setTitles(titles);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class RetrieveInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float input) {
            return Math.abs(1 - input);
        }
    }

}
