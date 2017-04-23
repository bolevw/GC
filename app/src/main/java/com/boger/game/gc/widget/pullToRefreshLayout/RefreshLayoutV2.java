package com.boger.game.gc.widget.pullToRefreshLayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.boger.game.gc.R;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * Created by liubo on 2017/4/17.
 */

public class RefreshLayoutV2 extends ViewGroup {
    private static final String TAG = "RefreshLayoutV2";

    private View targetView;
    private View headerView;
    private int headerHeight;
    private int dragDistance;
    private boolean hasMeasureHeaderView;
    private int currentTargetOffset;
    private int lastTargetOffset;
    private int touchSlop;

    public RefreshLayoutV2(Context context) {
        this(context, null);
    }

    public RefreshLayoutV2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayoutV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.ic_load_fail);
        setHeader(imageView);
    }


    public void setHeader(View view) {
        if (view != null && view != headerView) {
            removeView(headerView);

            LayoutParams lp = view.getLayoutParams();
            if (lp == null) {
                lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(lp);
            }
            addView(view);
            headerView = view;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (targetView == null) {
            ensureTargetView();
        }
        if (targetView == null) {
            return;
        }

        targetView.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
        measureChild(headerView, widthMeasureSpec, heightMeasureSpec);
        if (!hasMeasureHeaderView) {
            headerHeight = headerView.getMeasuredHeight();
            dragDistance = headerHeight;
            hasMeasureHeaderView = true;
        }

    }

    private void ensureTargetView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (!view.equals(headerView)) {
                targetView = view;
                break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (targetView == null) {
            ensureTargetView();
        }
        if (targetView == null) {
            return;
        }

        headerView.layout(getMeasuredWidth() / 2 - headerView.getMeasuredWidth() / 2, 0, getMeasuredWidth() / 2 + headerView.getMeasuredWidth() / 2, headerHeight);
        int childHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int childWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int top = getPaddingTop() + currentTargetOffset;
        int left = getPaddingLeft();
        int right = left + childWidth;
        int bottom = top + childHeight;
        targetView.layout(left, top, right, bottom);
    }

    private int lastDownX, lastDownY;
    private MotionEvent lastEv;
    private int activePointerId;
    private boolean isTouch, isBeginDrag;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled() || targetView == null) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(0);
                lastDownX = (int) ev.getX(0);
                lastDownY = (int) ev.getY(0);
                currentTargetOffset = targetView.getTop();
                lastTargetOffset = currentTargetOffset;
                isTouch = true;
                isBeginDrag = false;
                super.dispatchTouchEvent(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (activePointerId == MotionEvent.INVALID_POINTER_ID) {
                    Log.e(TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return super.dispatchTouchEvent(ev);
                }
                lastEv = ev;
                float x = ev.getX(MotionEventCompat.findPointerIndex(ev, activePointerId));
                float y = ev.getY(MotionEventCompat.findPointerIndex(ev, activePointerId));
                float xDiff = x - lastDownX;
                float yDiff = y - lastDownY;
                float offset = yDiff * 0.5f;
                if (!isBeginDrag && Math.abs(yDiff) > touchSlop && Math.abs(yDiff) > Math.abs(xDiff)) {
                    isBeginDrag = true;
                }
                if (isBeginDrag) {
                    boolean moveDown = offset > 0;
                    boolean canMoveDown = canChildScrollUp();
                    boolean moveUp = !moveDown;
                    boolean canMoveUp = currentTargetOffset > 0;
                    if ((moveDown && !canMoveDown) || (moveUp && canMoveUp)) {
                        moveSpinner(offset);
                        lastDownY = (int) y;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isTouch = false;
                activePointerId = MotionEvent.INVALID_POINTER_ID;
                finishSpinner();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                activePointerId = MotionEventCompat.getActionIndex(ev);
                if (activePointerId < INVALID_POINTER) {
                    Log.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                activePointerId = MotionEventCompat.getPointerId(ev, activePointerId);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == activePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            lastDownX = (int) ev.getX(newPointerIndex);
            lastDownY = (int) ev.getY(newPointerIndex);
            activePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }


    private void moveSpinner(float offset) {
        offset = Math.round(offset);
        if (offset == 0) {
            return;
        }
        int targetY = (int) Math.max(0, currentTargetOffset + offset);
        offset = targetY - currentTargetOffset;
        setTargetOffsetTopAndBottom((int) offset);
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        if (offset == 0) {
            return;
        }
        targetView.offsetTopAndBottom(offset);

        lastTargetOffset = currentTargetOffset;
        currentTargetOffset = targetView.getTop();

        invalidate();
    }

    private void finishSpinner() {
     /*   if (state == PtrState.LOADING) {
            if (currentTargetOffsetTop > totalDragDistance) {
                autoScroll.scrollTo(totalDragDistance, 500);
            }
        } else {
            autoScroll.scrollTo(0, 500);
        }*/
    }

    /**
     * quote from swipeRefreshLayout
     *
     * @return
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targetView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(targetView, -1) || targetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targetView, -1);
        }
    }

    class AutoScroll implements Runnable {

        @Override
        public void run() {

        }

    }
}
