package com.example.administrator.gc.widget.lable_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by liubo on 2016/5/26.
 */
public class LabelViewHelper {
    /*
    orientation
     */
    private static final int LEFT_TOP = 1;
    private static final int RIGHT_TOP = 2;
    private static final int LEFT_BOTTOM = 3;
    private static final int RIGHT_BOTTOM = 4;

    private static final int DEFAULT_TEXT_COLOR = Color.RED;
    private static final int DEFAULT_BACKGROUND = 0x9F27CDC0;
    private static final int DEFAULT_TEXT_SIZE = 14;
    private static final int DEFAULT_HEIGHT = 22;
    private static final int DEFAULT_DISTANCE = 40;
    private static final int DEFAULT_ORIENTATION = LEFT_TOP;

    private Paint rectPaint;
    private Path mPath;
    private Paint textPaint;
    private Rect textBounds;
    private Context context;

    private float startPosX;
    private float startPosY;
    private float endPosX;
    private float endPosY;
    private int backgroundColor = DEFAULT_BACKGROUND;
    private int textColor = DEFAULT_TEXT_COLOR;
    private int textSize;
    private int distance;
    private int height;
    private int orientation = DEFAULT_ORIENTATION;
    private String text = "thi is a tag";


    public LabelViewHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        textSize = dip2Px(DEFAULT_TEXT_SIZE);
        distance = dip2Px(DEFAULT_DISTANCE);
        height = dip2Px(DEFAULT_HEIGHT);

        rectPaint = new Paint();
        rectPaint.setDither(true);
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeJoin(Paint.Join.ROUND);
        rectPaint.setStrokeCap(Paint.Cap.SQUARE);

        mPath = new Path();
        mPath.reset();

        textPaint = new Paint();
        textPaint.setDither(true);
        textPaint.setAntiAlias(true);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeCap(Paint.Cap.SQUARE);

        textBounds = new Rect();
    }

    public void draw(Canvas canvas, int measuredWidth, int measuredHeight) {
        if (canvas == null) {
            return;
        }
        float actualDistance = distance + height / 2;
        calcOffset(actualDistance, measuredWidth, measuredHeight);

        rectPaint.setColor(backgroundColor);
        rectPaint.setStrokeWidth(height);

        mPath.reset();
        mPath.moveTo(startPosX, startPosY);
        mPath.lineTo(endPosX, endPosY);
        canvas.drawPath(mPath, rectPaint);

        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(text, 0, text.length(), textBounds);

        canvas.drawTextOnPath(text, mPath, (1.4142135f * actualDistance) / 2 - textBounds.width() / 2, textBounds.height() / 2, textPaint);
    }

    private void calcOffset(float actualDistance, int measuredWidth, int measuredHeight) {
        switch (orientation) {
            case LEFT_TOP:
                startPosX = 0;
                startPosY = actualDistance;
                endPosX = actualDistance;
                endPosY = 0;
                break;
            case LEFT_BOTTOM:
                startPosX = 0;
                startPosY = measuredHeight - actualDistance;
                endPosX = actualDistance;
                endPosY = measuredHeight;
                break;
            case RIGHT_TOP:
                startPosX = measuredWidth - actualDistance;
                startPosY = 0;
                endPosX = measuredWidth;
                endPosY = actualDistance;
                break;
            case RIGHT_BOTTOM:
                startPosX = measuredWidth - actualDistance;
                startPosY = measuredHeight;
                endPosX = measuredWidth;
                endPosY = measuredHeight - actualDistance;
                break;
        }
    }

    private int dip2Px(float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}
