package com.example.administrator.gc.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.example.administrator.gc.R;

/**
 * Created by Administrator on 2016/3/22.
 */
public class VPIndicator extends LinearLayout {

    private int count;
    private Context context;

    private Animator animIn;
    private Animator animOut;

    public VPIndicator(Context context) {
        super(context);
    }

    public VPIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        setPadding(0, 0, dip2px(16), 0);

        animIn = AnimatorInflater.loadAnimator(context, R.animator.vp_scale_with_alpha);
        animIn.setDuration(300);

        animOut = AnimatorInflater.loadAnimator(context, R.animator.vp_scale_with_alpha);
        animOut.setInterpolator(new RetrieveInterpolator());
        animOut.setDuration(300);
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        createIndicator(count);
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
        currentPosition = position;
        out(v);
        in(v2);

    }

    public void in(View v) {
        if (animOut.isRunning())
            animOut.end();
        if (animIn.isRunning())
            animIn.end();

        animIn.setTarget(v);
        animIn.start();
    }

    public void out(View v) {
        if (animIn.isRunning())
            animIn.end();
        if (animOut.isRunning())
            animOut.end();

        animOut.setTarget(v);
        animOut.start();
    }

    public int dip2px(int value) {
        if (value < 0) {
            value = 5;
        }
        return (int) (getResources().getDisplayMetrics().density * value + 0.5f);
    }

    private class RetrieveInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float input) {
            return Math.abs(1 - input);
        }
    }

}
