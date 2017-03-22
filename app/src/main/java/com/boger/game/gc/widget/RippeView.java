package com.boger.game.gc.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/5/3.
 */
public class RippeView extends View implements ValueAnimator.AnimatorUpdateListener {

    private Rect mRect = new Rect();
    private Paint mPaint;
    private ValueAnimator valueAnimator;
    private Point mTouchPoint = new Point();
    private float mRadius = 0f;

    public RippeView(Context context) {
        super(context);
    }

    public RippeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, 100);
        valueAnimator.setDuration(400);
        valueAnimator.addUpdateListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mTouchPoint.x, mTouchPoint.y, mRadius, mPaint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        float per = value / 100f;
       /* if (mTouchPoint == null) {
            mTouchPoint.set(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        }*/
        int r = Math.max(getMeasuredHeight(), getMeasuredWidth());
        //   mRect.set((int) (mTouchPoint.x * per), (int) (mTouchPoint.y * per), (int) (getMeasuredWidth() - mTouchPoint.x * per), (int) (getMeasuredHeight() - mTouchPoint.y * per));

        mRadius = r * per;
        this.invalidate();

        if (per == 1f) {
            mRadius = 0f;
            this.invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchPoint.set((int) event.getX(), (int) event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start();
                    }
                }, 700);
                break;
            case MotionEvent.ACTION_UP:


                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return true;
    }

    public void start() {
        valueAnimator.start();
    }
}
