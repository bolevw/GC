package com.boger.game.gc.widget.lable_view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by liubo on 2016/5/26.
 */
public class LabelImageView extends ImageView {
    private LabelViewHelper helper;


    public LabelImageView(Context context) {
        this(context, null);
    }

    public LabelImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        helper = new LabelViewHelper(context, attrs, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        helper.draw(canvas, getMeasuredWidth(), getMeasuredHeight());
    }
}
