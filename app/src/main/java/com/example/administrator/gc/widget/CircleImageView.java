package com.example.administrator.gc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.gc.R;

/**
 * 通过在原来的imageView增加一个layer，然后通过XFerMode.SRC_IN"
 * Created by Administrator on 2016/3/25.
 */
public class CircleImageView extends ImageView {

    private static final int STYLE_CIRCLE = 0;
    private static final int STYLE_ROUNDRECT = 1;

    private int style = STYLE_CIRCLE;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, 0, 0);
        style = a.getInt(R.styleable.CircleImageView_style, STYLE_CIRCLE);
        setScaleType(ScaleType.FIT_XY);
        a.recycle();
    }

    protected void onDraw(Canvas canvas) {
        Drawable maiDrawable = getDrawable();
        float mCornerRadius = 6 * getContext().getResources().getDisplayMetrics().density;  //圆角半径
        if (maiDrawable instanceof BitmapDrawable && mCornerRadius > 0) {
            Paint paint = ((BitmapDrawable) maiDrawable).getPaint();
            final int color = 0xff000000;

            final RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            int saveCount = canvas.saveLayer(rectF, null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                    | Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            if (style == STYLE_CIRCLE) {
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
            } else {
                canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint);
            }

            Xfermode oldMode = paint.getXfermode();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            super.onDraw(canvas);
            paint.setXfermode(oldMode);
            canvas.restoreToCount(saveCount);
        } else {
            super.onDraw(canvas);
        }
    }
}
