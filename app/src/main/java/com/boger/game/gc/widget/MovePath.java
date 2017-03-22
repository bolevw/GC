package com.boger.game.gc.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by liubo on 2016/6/13.
 */
public class MovePath extends View {

    private Path mPath;
    private Paint mPaint;
    private float percentage;
    private float pathLength;


    public MovePath(Context context) {
        this(context, null);
    }

    public MovePath(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovePath(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(20f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);


        mPath = new Path();
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(100, 100);
        mPath.lineTo(180, 0);
        PathMeasure pathMeasure = new PathMeasure(mPath, false);

        pathLength = pathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{pathLength, pathLength}, pathLength - percentage * pathLength);
        mPaint.setPathEffect(dashPathEffect);
        canvas.save();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    public void run() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "percentage", 0f, 1f);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
        this.invalidate();
    }
}
