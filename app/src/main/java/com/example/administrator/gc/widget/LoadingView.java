package com.example.administrator.gc.widget;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.gc.R;

/**
 * Created by Administrator on 2016/3/26.
 */
public class LoadingView extends ViewGroup {

    private static final String TAG = "LoadingView";

    private static final int DEFAULT_COUNT = 4;

    private int pointCount;

    private ImageView moveView;

    private Context mContext;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setWillNotDraw(true);
        Log.d("LoadingView", "LoadingView");
        init();
    }

    private void init() {
        setPointCount(DEFAULT_COUNT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            childView.measure(MeasureSpec.makeMeasureSpec(childView.getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childView.getMeasuredHeight(), MeasureSpec.EXACTLY));
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int itemMargin = width / pointCount;

        Log.d("LoadingView", "size" + getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(itemMargin * i, getHeight() / 2, getWidth() - itemMargin * i - view.getWidth(), view.getMeasuredHeight() + getHeight() / 2);
        }
    }


    @Override
    protected Parcelable onSaveInstanceState() {

        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    public void setPointCount(int count) {
        this.pointCount = count;
        moveView = new ImageView(this.mContext);
        moveView.setImageResource(R.mipmap.ic_icon);
        moveView.setAlpha(1.0f / (pointCount + 1));
        ViewGroup.LayoutParams lp = new LayoutParams(dip2px(40), dip2px(40));
        this.addView(moveView, lp);
        createPoint(count);
    }

    private void createPoint(int count) {
        for (int i = 0; i < count; i++) {
            View view = new View(this.mContext);
            view.setBackgroundResource(R.drawable.orange_circle);
            ViewGroup.LayoutParams lp = new LayoutParams(dip2px(8), dip2px(8));
            this.addView(view, lp);
        }
    }

    private int dip2px(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
