package com.boger.game.gc.widget.Test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.boger.game.gc.R;

/**
 * Created by liubo on 2017/2/9.
 */

public class BorderTextView extends View {

    private String title;
    private int textSize;
    private int textColor;
    private int src;

    private Paint paint;
    private Bitmap image;
    private int mWidth;
    private int mHeight;
    private Rect mTextBound;


    public BorderTextView(Context context) {
        this(context, null);
    }

    public BorderTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView, 0, 0);

        title = ta.getString(R.styleable.BorderTextView_text);
        textSize = ta.getDimensionPixelSize(R.styleable.BorderTextView_textSize, 14);
        textColor = ta.getColor(R.styleable.BorderTextView_textColor, Color.BLACK);
        src = ta.getResourceId(R.styleable.BorderTextView_src, R.mipmap.ic_launcher);
        image = BitmapFactory.decodeResource(getResources(), src);
        ta.recycle();

        mTextBound = new Rect();
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.getTextBounds(title, 0, title.length(), mTextBound);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            float textWidth = mTextBound.width() + getPaddingLeft() + getPaddingRight();
            float imageWidth = image.getWidth() + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                mWidth = (int) Math.min(widthSize, Math.max(textWidth, imageWidth));
            }

        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            float textHeight = mTextBound.height() + getPaddingTop() + getPaddingBottom();
            float imageHeight = image.getHeight();
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = (int) Math.min(heightSize, textHeight + imageHeight);
            }
        }

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth, mHeight, paint);

        if (mTextBound.width() > mWidth) {
            String tempTitle = TextUtils.ellipsize(title, new TextPaint(paint), mWidth - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawBitmap(image, 0, 0, paint);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(tempTitle, 0, mHeight - image.getHeight(), paint);
        } else {
            canvas.drawBitmap(image, 0, 0, paint);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setStrokeWidth(1);
            canvas.drawText(title, mWidth / 2, (image.getHeight()), paint);
        }

    }
}
