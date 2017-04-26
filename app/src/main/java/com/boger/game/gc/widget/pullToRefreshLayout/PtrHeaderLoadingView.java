package com.boger.game.gc.widget.pullToRefreshLayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

/**
 * Created by liubo on 2017/4/11.
 */

public class PtrHeaderLoadingView extends View implements PtrHeader {
    private static final String TAG = "LoadingView";
    private static final int DEFAULT_COLOR = Color.RED;
    private static final int DEFAULT_DURATION = 600;

    private Paint mPaint;
    private Path mPath;
    private Path mRowPath;
    private int mMaxRadius;
    private int mMinRadius;
    private int mMinWidth;

    private ValueAnimator mCircleChangeAnim;
    private float fraction = 1f;
    private int maxDistance;
    private int degree = 0;
    private ValueAnimator animator = new ValueAnimator();

    public PtrHeaderLoadingView(Context context) {
        this(context, null);
    }

    public PtrHeaderLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrHeaderLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(DEFAULT_COLOR);

        mPath = new Path();
        mRowPath = new Path();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mMinWidth = Math.min(dm.widthPixels, dm.heightPixels) / 4; //取宽高中的最小值


        mCircleChangeAnim = new ValueAnimator();
        mCircleChangeAnim.setFloatValues(1f, 1.2f, 1f);
        mCircleChangeAnim.setInterpolator(new LinearInterpolator());
        mCircleChangeAnim.setDuration(DEFAULT_DURATION);
        mCircleChangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        animator.setIntValues(0, 90, 180);
        animator.setDuration(DEFAULT_DURATION);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (int) animation.getAnimatedValue();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize(mMinWidth, widthMeasureSpec), resolveSize(mMinWidth, heightMeasureSpec));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxRadius = Math.min(w, h) / 5;
        mMinRadius = mMaxRadius * 2 / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
        转换坐标系变成正常的远点在中间，y轴朝上
         */
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.scale(1, -1);
        mPath.reset();
        mRowPath.reset();
        drawCircles(canvas);
    }


    private void drawCircles(final Canvas canvas) {

        canvas.rotate(degree);
        maxDistance = mMinRadius;
        float v = (200f / 44f) * fraction * fraction - (200f / 44f);
        canvas.drawCircle((v) * maxDistance, 0, mMinRadius, mPaint);
        canvas.drawCircle(-(v) * maxDistance, 0, mMinRadius, mPaint);
        canvas.drawCircle(0, (v) * maxDistance, mMinRadius, mPaint);
        canvas.drawCircle(0, -(v) * maxDistance, mMinRadius, mPaint);

        mPath.moveTo(v * maxDistance, mMinRadius);
        mPath.quadTo(0, 0, -v * maxDistance, mMinRadius);
        mPath.lineTo(-v * maxDistance, -mMinRadius);
        mPath.quadTo(0, 0, v * maxDistance, -mMinRadius);
        mPath.lineTo(v * maxDistance, mMinRadius);
        mPath.close();

        mRowPath.moveTo(mMinRadius, v * maxDistance);
        mRowPath.lineTo(-mMinRadius, v * maxDistance);
        mRowPath.quadTo(0, 0, -mMinRadius, -v * maxDistance);
        mRowPath.lineTo(mMinRadius, -v * maxDistance);
        mRowPath.quadTo(0, 0, mMinRadius, v * maxDistance);
        mRowPath.close();
        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(mRowPath, mPaint);
    }


    public void start() {
        if (mCircleChangeAnim.isRunning()) {
            return;
        }
        fraction = 1f;
        animator.setRepeatCount(Integer.MAX_VALUE);
        mCircleChangeAnim.setRepeatCount(Integer.MAX_VALUE);
        animator.start();
        mCircleChangeAnim.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    public void stop() {
        fraction = 1f;
        degree = 0;
        invalidate();
        animator.cancel();
        mCircleChangeAnim.cancel();
    }

    @Override
    public void reset() {
        stop();
    }

    @Override
    public void pull() {
        stop();
    }

    @Override
    public void onPulling(float currentPos, float lastPos, float refreshPos, boolean isTouch, PtrState state) {
        float percent = Math.max(1f, Math.min(0f, Math.abs(refreshPos - currentPos) / refreshPos));

        if (state == PtrState.PULL && isTouch) {
            degree = (int) (180 * percent);
            fraction = 1.2f * percent;
            invalidate();
        }

    }

    @Override
    public void loading() {
        start();
    }

    @Override
    public void complete() {
        stop();
    }
}
