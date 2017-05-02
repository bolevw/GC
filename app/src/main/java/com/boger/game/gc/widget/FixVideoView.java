package com.boger.game.gc.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by liubo on 2017/5/1.
 */

public class FixVideoView extends VideoView {
    public FixVideoView(Context context) {
        super(context);
    }

    public FixVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FixVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
}
