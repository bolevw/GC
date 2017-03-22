package com.boger.game.gc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by liubo on 2017/2/16.
 */

public class StickyHeaderViewGroup extends ViewGroup {
    private static final String TAG = "StickyHeaderViewGroup";

    private int mHeaderMinHeight = 80;

    private Scroller mScroller;
    private int mTouchSlop;
    private int mScreenWidth;
    private int mScreenHeight;
    private View mHeader;
    private VelocityTracker mVelocityTracker;
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
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mVelocityTracker = VelocityTracker.obtain();
        mHeaderMinHeight = (int) (context.getResources().getDisplayMetrics().density * mHeaderMinHeight + 0.5f);
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
            mBody.layout(0, mHeader.getMeasuredHeight(), mBody.getMeasuredWidth(), mHeader.getMeasuredHeight() + mBody.getMeasuredHeight() - (mHeader.getMeasuredHeight() - mHeaderMinHeight));
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
                result = false;
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (shouldIntercept(ev)) {
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

    private boolean shouldIntercept(MotionEvent ev) {
        boolean result = false;
        if (mHeader.getMeasuredHeight() + mHeader.getTop() > mHeaderMinHeight) {
            result = Math.abs(ev.getRawY() - mDownY) >= mTouchSlop;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
                mDownY = event.getRawY();

                if (!mScroller.isFinished()) {
                    mScroller.forceFinished(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                int disY = (int) (event.getRawY() - mDownY);
//                scrollBy(0, (int) -disY);

                layoutChild(disY, mHeader);
                layoutChild(disY, mBody);
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_CANCEL:
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(1000);
                float speedY = mVelocityTracker.getYVelocity();
                if (Math.abs(speedY) >= mTouchSlop) {
                    doFling(-speedY);
                }
                Log.e(TAG, "onTouchEvent: " + getScrollY() + " top" + getTop());
                recycleVelocityTracker();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void layoutChild(int i, View view) {
        if (mHeader.getTop() > 0) {
            mHeader.layout(0, 0, mHeader.getMeasuredWidth(), mHeader.getMeasuredHeight());
            mBody.layout(0, mHeader.getMeasuredHeight(), mBody.getMeasuredWidth(), mHeader.getMeasuredHeight() + mBody.getMeasuredHeight() - (mHeader.getMeasuredHeight() - mHeaderMinHeight));
            return;
        }
        if (mHeader.getMeasuredHeight() + mHeader.getTop() < mHeaderMinHeight) {
            mHeader.layout(mHeader.getLeft(), -mHeader.getMeasuredHeight() + mHeaderMinHeight, mHeader.getRight(), mHeaderMinHeight);
            mBody.layout(mBody.getLeft(), mHeaderMinHeight, mBody.getRight(), mHeaderMinHeight + mBody.getMeasuredHeight());
            return;
        }
        view.layout(view.getLeft(), view.getTop() + i, view.getRight(), view.getBottom() + i);

    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            try {
                mVelocityTracker.clear();
                mVelocityTracker.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doFling(float v) {
        if (mScroller != null) {
            mScroller.forceFinished(true);
            mScroller.fling(0, getScrollY(), 0, (int) v, 0, 0, 0, 0);
            invalidate();
        }
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


}
