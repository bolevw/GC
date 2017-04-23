package com.boger.game.gc.widget.stickLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by liubo on 2017/4/17.
 */

public class StickLayout extends ViewGroup {

    private static final String TAG = "StickLayout";


    public StickLayout(Context context) {
        super(context);
    }

    public StickLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
