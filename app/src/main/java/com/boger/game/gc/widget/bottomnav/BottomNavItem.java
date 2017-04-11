package com.boger.game.gc.widget.bottomnav;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;

/**
 * Created by liubo on 2016/3/21.
 */
public class BottomNavItem extends LinearLayout {

    private ImageView itemNavBottomImageView;
    private TextView itemNavBottomTextView;

    private String text;
    private int iconRes;

    private Animator animatorIn;
    private Animator animatorOut;
    private boolean isAnimation = false;


    public BottomNavItem(Context context) {
        super(context);
    }

    public BottomNavItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomNavItem, 0, 0);
        text = ta.getString(R.styleable.BottomNavItem_bni_text);
        iconRes = ta.getResourceId(R.styleable.BottomNavItem_bni_icon, 0);
        ta.recycle();



        animatorIn = AnimatorInflater.loadAnimator(context, R.animator.scale_with_alpha);
        animatorIn.setDuration(300);

        animatorOut = AnimatorInflater.loadAnimator(context, R.animator.scale_with_alpha);
        animatorOut.setInterpolator(new RetrieveInterpolator());
        animatorOut.setDuration(300);
    }

    public void in() {
        if (animatorOut.isRunning())
            animatorOut.end();
        if (animatorIn.isRunning())
            animatorIn.end();

        animatorIn.setTarget(itemNavBottomImageView);
        animatorIn.start();
        itemNavBottomImageView.setEnabled(false);
    }

    public void out() {
        if (animatorIn.isRunning())
            animatorIn.end();
        if (animatorOut.isRunning())
            animatorOut.end();

        animatorOut.setTarget(itemNavBottomImageView);
        animatorOut.start();
        itemNavBottomImageView.setEnabled(true);
    }

    private class RetrieveInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float input) {
            return Math.abs(1 - input);
        }
    }
}
