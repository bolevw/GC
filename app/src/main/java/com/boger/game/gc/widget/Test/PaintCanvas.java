package com.boger.game.gc.widget.Test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liubo on 2017/1/11.
 */

public class PaintCanvas extends View {
    private Paint mPaint;
    private Path mPath;
    private PathEffect[] mPathEffet = new PathEffect[7];
    private float mPhase = 5;

    public PaintCanvas(Context context) {
        super(context);
    }

    public PaintCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public PaintCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        initPath();
    }

    public void initPath() {
        mPath = new Path();

        mPath.moveTo(10, 50);
        for (int i = 0; i < 30; i++) {
            mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }
        mPathEffet[0] = null;
        mPathEffet[1] = new CornerPathEffect(10);
        mPathEffet[2] = new DashPathEffect(new float[]{20, 10}, mPhase);
        mPathEffet[3] = new DiscretePathEffect(40f, 0f);
        Path path = new Path();
        path.addRect(0, 0, 4, 4, Path.Direction.CW);
        mPathEffet[4] = new PathDashPathEffect(path, 20, mPhase, PathDashPathEffect.Style.ROTATE);
        mPathEffet[5] = new ComposePathEffect(mPathEffet[2], mPathEffet[4]);
        mPathEffet[6] = new SumPathEffect(mPathEffet[4], mPathEffet[3]);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mPathEffet.length; i++) {
            mPaint.setPathEffect(mPathEffet[i]);
            canvas.drawPath(mPath, mPaint);
            canvas.translate(0, 250);
        }
    }
}
