package com.boger.game.gc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.boger.game.gc.R;

/**
 * Created by Administrator on 2016/3/22.
 */
public class FixRatioFrameLayout extends FrameLayout {

    private boolean baseOnWith;
    private float ratio;

    public FixRatioFrameLayout(Context context) {
        super(context);
    }

    public FixRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FixRatioFrameLayout, 0, 0);

        baseOnWith = ta.getBoolean(R.styleable.FixRatioFrameLayout_baseOnWith, false);
        ratio = ta.getFloat(R.styleable.FixRatioFrameLayout_ratio, 1);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (baseOnWith) {
            int size = MeasureSpec.getSize(widthMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (size * ratio), MeasureSpec.EXACTLY);
        } else {
            int size = MeasureSpec.getSize(heightMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (size * ratio), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
