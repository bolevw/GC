package com.example.administrator.gc.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liubo on 2017/1/3.
 */

public class ScrollButton extends View implements View.OnClickListener {
    private Paint mPaint;
    private Path mRoundRectPath;

    private OnToggleChangeListener listener;
    private boolean on;

    public ScrollButton(Context context) {
        super(context);
        init(context);
    }

    public ScrollButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mRoundRectPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        mRoundRectPath.addCircle(width / 4, width / 4 + height / 4, width / 4, Path.Direction.CW);
        mRoundRectPath.addRect(width / 4, 0 + height / 4, width / 4 + width / 2, height / 4 + width / 2, Path.Direction.CW);
        mRoundRectPath.addCircle(width / 4 + width / 2, width / 4 + height / 4, width / 4, Path.Direction.CW);
        mPaint.setColor(Color.RED);
        canvas.drawPath(mRoundRectPath, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(width / 4, width / 4 + height / 4, width / 3, mPaint);

    }

    @Override
    public void onClick(View v) {
        listener.toggleChange(on);
    }

    public void setListener(OnToggleChangeListener listener) {
        this.listener = listener;
    }

    public interface OnToggleChangeListener {
        void toggleChange(boolean on);
    }
}
