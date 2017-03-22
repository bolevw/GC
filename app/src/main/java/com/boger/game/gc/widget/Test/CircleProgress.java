package com.boger.game.gc.widget.Test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.boger.game.gc.R;

/**
 * Created by liubo on 2017/2/10.
 */

public class CircleProgress extends View implements View.OnClickListener {
    private static final String TAG = "CircleProgress"; //android studio 中输入logt快速输出logtag

    private Paint paint;
    private boolean isNext = false;
    private int speed;
    private int circleRadius;
    private int firstColor;
    private int secondColor;

    private int mWidth;
    private int mHeight;
    private int windowWidth;
    private int windowHeight;
    private float progress = 0f;
    private Thread mThread;
    private boolean start;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        speed = ta.getInteger(R.styleable.CircleProgress_speed, 1 * 1);
        circleRadius = ta.getInteger(R.styleable.CircleProgress_radius, 20);
        firstColor = ta.getColor(R.styleable.CircleProgress_firstColor, Color.RED);
        secondColor = ta.getColor(R.styleable.CircleProgress_secondColor, Color.BLACK);
        ta.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point p = new Point();
        wm.getDefaultDisplay().getSize(p);
        windowHeight = p.y;
        windowWidth = p.x;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    progress++;
                    if (progress == 360) {
                        progress = 0;
                        if (!isNext) {
                            isNext = true;
                        } else {
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = windowWidth / 3;
            }
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            if (widthMode == MeasureSpec.AT_MOST) {
                mWidth = windowWidth / 3;
            }
        }

        if (circleRadius > mHeight || circleRadius > mWidth || circleRadius == 0) {
            circleRadius = mHeight - getPaddingTop() - getPaddingBottom();
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setStrokeWidth(circleRadius);
        RectF rect = new RectF();
        rect.left = getPaddingLeft() + circleRadius;
        rect.top = getPaddingTop() + circleRadius;
        rect.right = mWidth - getPaddingRight() - circleRadius;
        rect.bottom = mHeight - getPaddingBottom() - circleRadius;
        if (!isNext) {
            paint.setColor(firstColor);
            canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - circleRadius, paint);
            paint.setColor(secondColor);
            canvas.drawArc(rect, -90f, progress, false, paint);
        } else {
            paint.setColor(secondColor);
            canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - circleRadius, paint);
            paint.setColor(firstColor);
            canvas.drawArc(rect, -90f, progress, false, paint);
        }


    }

    @Override
    public void onClick(View v) {
        if (start) {
            return;
        } else {
            mThread.start();
            start = true;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (start) {
            mThread.interrupt();
        }
    }
}
