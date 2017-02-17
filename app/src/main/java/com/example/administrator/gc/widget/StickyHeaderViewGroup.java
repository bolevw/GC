package com.example.administrator.gc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by liubo on 2017/2/16.
 */

public class StickyHeaderViewGroup extends ViewGroup {
    private static final String TAG = "StickyHeaderViewGroup";
    private int mHeaderMinHeight = 30;

    private Scroller mScroller;
    private int mTouchSloup;
    private int mScreenWidth;
    private int mScreenHeight;
    private View mHeader;
    private View mBody;

    private float mDownX;
    private float mDownY;

    public StickyHeaderViewGroup(Context context) {
        this(context, null);
    }

    public StickyHeaderViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyHeaderViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mScroller = new Scroller(context);
        mTouchSloup = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            mHeader.layout(0, 0, mHeader.getMeasuredWidth(), mHeader.getMeasuredHeight());
            mBody.layout(0, mHeader.getMeasuredHeight(), mBody.getMeasuredWidth(), mHeader.getMeasuredHeight() + mBody.getMeasuredHeight());
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new InflateException("only contain 2 child");
        }
        mHeader = getChildAt(0);
        mBody = getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.forceFinished(true);

                }
                result = false;
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isBodyInTopPlace(ev)) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                result = false;
                break;
        }
        return result;
    }

    private boolean isBodyInTopPlace(MotionEvent ev) {
        boolean result = false;
        float disY = ev.getRawY() - mDownY;
        if (mBody.getTop() > 0) {
            Log.e(TAG, "isBodyInTopPlace: top" + mBody.getTop());
            result = Math.abs(disY) > mTouchSloup;
        } else {
            result = false;
        }
        mDownY = ev.getRawY();
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getRawY();
                return true;

            case MotionEvent.ACTION_MOVE:
                float disY = event.getRawY() - mDownY;
                scrollBy(0, (int) -disY);
                Log.e(TAG, "onTouchEvent: disy " + disY);
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                disY = event.getRawY() - mDownY;
                mScroller.startScroll(getScrollX(), getScrollY(), 0, (int) -disY);
                mDownY = event.getRawY();
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if (!mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        }
    }


}
