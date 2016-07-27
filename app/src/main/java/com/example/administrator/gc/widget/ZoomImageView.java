package com.example.administrator.gc.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by liubo on 16/7/25.
 */

public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private boolean get = false;
    private float mInitScale;
    private float mMidScale;
    private float mMaxScale;
    private Matrix mMatrix;
    private ScaleGestureDetector scaleGestureDetector;
    private Drawable drawable;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setScaleType(ScaleType.MATRIX);
        mMatrix = new Matrix();
        scaleGestureDetector = new ScaleGestureDetector(context, this);
    }

    @Override
    public void onGlobalLayout() {
        if (!get) {
            int width = getWidth();
            int height = getHeight();
            drawable = getDrawable();
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            float scale = 1.0f;

            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }

            if (dh > height && dw > width) {
                scale = Math.min(height * 1.0f / dh, width * 1.0f / dw);
            }

            if (dh < height && dw < width) {
                scale = Math.min(height * 1.0f / dh, width * 1.0f / dw);
            }

            mInitScale = scale;
            mMidScale = scale * 2;
            mMaxScale = scale * 4;
            int dx = getWidth() / 2 - drawable.getIntrinsicWidth() / 2;
            int dy = getHeight() / 2 - drawable.getIntrinsicHeight() / 2;

            mMatrix.postTranslate(dx, dy);
            mMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mMatrix);
            get = true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 获取缩放值
     *
     * @return
     */
    public float getScale() {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scale = getScale();
        float scaleFactor = scaleGestureDetector.getScaleFactor();

        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {

            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }

            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
        }
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
