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
import android.widget.Scroller;

import com.boger.game.gc.utils.ToastUtils;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * Created by liubo on 2017/4/11.
 */

public class PullToRefreshLayout extends ViewGroup {
    private static final float DRAG_RATE = .5f;
    private static final String TAG = "PullToRefreshLayout";

    private int mTouchSlop;
    private PtrState state = PtrState.RESET;

    private View headerView;
    private View targetView;
    private int currentTargetOffsetTop;
    private boolean hasMeasureHeader;
    private int headerHeight;
    private int totalDragDistance;

    public PullToRefreshLayout(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        setHeader(new PtrHeaderLoadingView(context));

        autoScroll = new AutoScroll();
    }

    public void setHeader(View view) {
        if (view != null && view != headerView) {
            removeView(headerView);

            // 为header添加默认的layoutParams
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(layoutParams);
            }
            headerView = view;
            addView(headerView);
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
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingBottom() - getPaddingTop(), MeasureSpec.EXACTLY));
        measureChild(headerView, widthMeasureSpec, heightMeasureSpec);
        if (!hasMeasureHeader) {
            hasMeasureHeader = true;
            headerHeight = headerView.getMeasuredHeight();
            totalDragDistance = headerHeight;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 0) {
            return;
        }
        if (targetView == null) {
            ensureTargetView();
        }

        if (targetView == null) {
            return;
        }

        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final View target = targetView;
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop() + currentTargetOffsetTop;
        int childWidth = width - getPaddingLeft() - getPaddingRight();
        int childHeight = height - getPaddingTop() - getPaddingBottom();
        target.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

        int headerWidth = headerView.getMeasuredWidth();
        headerView.layout(width / 2 - headerWidth / 2, -headerHeight + currentTargetOffsetTop, width / 2 + headerWidth / 2, currentTargetOffsetTop);
    }

    private void ensureTargetView() {
        if (targetView == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(headerView)) {
                    targetView = child;
                    break;
                }
            }
        }
    }

    private int activePointerId, lastTargetOffsetTop;
    private boolean isTouch, hasSendCancelEvent, mIsBeginDragged;
    private int lastDownX, lastDownY;

    private MotionEvent lastEv;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled() || targetView == null) {
            return super.dispatchTouchEvent(ev);
        }
        final int actionMask = ev.getActionMasked();
        switch (actionMask) {
            case MotionEvent.ACTION_DOWN:
                isAutoRefresh = false;
                autoScroll.stop();
                activePointerId = ev.getPointerId(0);
                isTouch = true;
                hasSendCancelEvent = false;
                mIsBeginDragged = false;
                lastTargetOffsetTop = currentTargetOffsetTop;
                currentTargetOffsetTop = targetView.getTop();
                lastDownX = (int) ev.getX(0);
                lastDownY = (int) ev.getY(0);
                super.dispatchTouchEvent(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (activePointerId == INVALID_POINTER) {
                    Log.e(TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return super.dispatchTouchEvent(ev);
                }

                lastEv = ev;
                float x = ev.getX(MotionEventCompat.findPointerIndex(ev, activePointerId));
                float y = ev.getY(MotionEventCompat.findPointerIndex(ev, activePointerId));
                float xDiff = x - lastDownX;
                float yDiff = y - lastDownY;
                float offsetY = yDiff * DRAG_RATE;
                if (!mIsBeginDragged && Math.abs(yDiff) > mTouchSlop && Math.abs(yDiff) > Math.abs(xDiff)) {
                    mIsBeginDragged = true;
                }

                if (mIsBeginDragged) {
                    boolean moveDwon = offsetY > 0;//
                    boolean canMovwDown = canChildScrollUp();
                    boolean moveUp = offsetY < 0;
                    boolean canMoveUp = currentTargetOffsetTop > 0;

                    if ((moveDwon && !canMovwDown) || (moveUp && canMoveUp)) {
                        moveSpinner(offsetY);
                        lastDownY = (int) y;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isTouch = false;
                activePointerId = INVALID_POINTER;
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

    private void finishSpinner() {
        if (state == PtrState.LOADING) {
            if (currentTargetOffsetTop > totalDragDistance) {
                autoScroll.scrollTo(totalDragDistance, 500);
            }
        } else {
            autoScroll.scrollTo(0, 500);
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == activePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            lastDownY = (int) ev.getY(newPointerIndex);
            lastDownX = (int) ev.getX(newPointerIndex);
            activePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private void moveSpinner(float offsetY) {
        int offset = Math.round(offsetY);
        if (offset == 0) {
            return;
        }

        if (!hasSendCancelEvent && isTouch && currentTargetOffsetTop > 0) {
            sendCancelEvent();
            hasSendCancelEvent = true;
        }

        int targetY = Math.max(0, currentTargetOffsetTop + offset); // target不能移动到小于0的位置……
        offset = targetY - currentTargetOffsetTop;
        // y = x - (x/2)^2
        float extraOS = targetY - totalDragDistance;
        float slingshotDist = totalDragDistance;
        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2) / slingshotDist);
        float tensionPercent = (float) (tensionSlingshotPercent - Math.pow(tensionSlingshotPercent / 2, 2));

        if (offset > 0) { // 下拉的时候才添加阻力
            offset = (int) (offset * (1f - tensionPercent));
            targetY = Math.max(0, currentTargetOffsetTop + offset);
        }


        // 1. 在RESET状态时，第一次下拉出现header的时候，设置状态变成PULL
        if (state == PtrState.RESET && currentTargetOffsetTop == 0 && targetY > 0) {
            changeState(PtrState.PULL);
            if (onRefreshListener != null) {
                onRefreshListener.onPull();
            }
        }

        // 2. 在PULL或者COMPLETE状态时，header回到顶部的时候，状态变回RESET
        if (currentTargetOffsetTop > 0 && targetY <= 0) {
            if (state == PtrState.PULL || state == PtrState.COMPLETE) {
                changeState(PtrState.RESET);
            }
        }

        // 3. 如果是从底部回到顶部的过程(往上滚动)，并且手指是松开状态, 并且当前是PULL状态，状态变成LOADING，这时候我们需要强制停止autoScroll
        if (state == PtrState.PULL && !isTouch && currentTargetOffsetTop > totalDragDistance && targetY <= totalDragDistance) {
            autoScroll.stop();
            changeState(PtrState.LOADING);
            if (onRefreshListener != null) {
                onRefreshListener.onRefresh();
            }
            // 因为判断条件targetY <= totalDragDistance，会导致不能回到正确的刷新高度（有那么一丁点偏差），调整change
            int adjustOffset = totalDragDistance - targetY;
            offset += adjustOffset;
        }

        setTargetOffsetTopAndBottom(offset);

        // 别忘了回调header的位置改变方法。
        if (headerView instanceof PtrHeader) {
            ((PtrHeader) headerView)
                    .onPulling(currentTargetOffsetTop, lastTargetOffsetTop, totalDragDistance, isTouch, state);
        }
    }

    private void sendCancelEvent() {
        if (lastEv == null) {
            return;
        }
        MotionEvent ev = MotionEvent.obtain(lastEv);
        ev.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(ev);
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        if (offset == 0) {
            return;
        }
        targetView.offsetTopAndBottom(offset);
        headerView.offsetTopAndBottom(offset);
        lastTargetOffsetTop = currentTargetOffsetTop;
        currentTargetOffsetTop = targetView.getTop();

        invalidate();
    }

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    private AutoScroll autoScroll;

    private class AutoScroll implements Runnable {
        private Scroller scroller;
        private int lastY;

        public AutoScroll() {
            scroller = new Scroller(getContext());
        }

        @Override
        public void run() {
            boolean finished = !scroller.computeScrollOffset() || scroller.isFinished();
            if (!finished) {
                int currY = scroller.getCurrY();
                int offset = currY - lastY;
                lastY = currY;
                moveSpinner(offset); // 调用此方法移动header和target
                post(this);
                onScrollFinish(false);
            } else {
                stop();
                onScrollFinish(true);
            }
        }

        public void scrollTo(int to, int duration) {
            int from = currentTargetOffsetTop;
            int distance = to - from;
            stop();
            if (distance == 0) {
                return;
            }
            scroller.startScroll(0, 0, 0, distance, duration);
            post(this);
        }

        private void stop() {
            removeCallbacks(this);
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
            lastY = 0;
        }
    }

    private void onScrollFinish(boolean isForceFinish) {
        if (isAutoRefresh && !isForceFinish) {
            isAutoRefresh = false;
            changeState(PtrState.LOADING);
            if (onRefreshListener != null) {
                onRefreshListener.onRefresh();
            }
            finishSpinner();
        }
    }


    private void changeState(PtrState state) {
        this.state = state;
        PtrHeader header = headerView instanceof PtrHeader ? (PtrHeader) headerView : null;
        if (header != null) {
            switch (state) {
                case RESET:
                    header.reset();
                    if (onRefreshListener != null) {
                        onRefreshListener.reset();
                    }
                    break;
                case PULL:
                    header.pull();
                    break;
                case LOADING:
                    header.loading();
                    break;
                case COMPLETE:
                    header.complete();
                    break;
            }
        }

        ToastUtils.showNormalToast(state.toString());
    }

    public void refreshComplete() {
        changeState(PtrState.COMPLETE);
        if (currentTargetOffsetTop == 0) {
            changeState(PtrState.RESET);
        } else {
            if (!isTouch) {
                postDelayed(delayToScrollTopRunnable, 500);
            }
        }
    }

    private Runnable delayToScrollTopRunnable = new Runnable() {
        @Override
        public void run() {
            autoScroll.scrollTo(0, 500);
        }
    };

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }


    public void autoRefresh() {
        autoRefresh(500);
    }

    public void autoRefresh(int time) {
        if (state != PtrState.RESET) {
            return;
        }
        postDelayed(autoRefreshRunnable, time);
    }

    private boolean isAutoRefresh = false;
    // 自动刷新，需要等View初始化完毕才调用，否则头部不会滚动出现
    private Runnable autoRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            // 标记当前是自动刷新状态，finishScroll调用时需要判断
            // 在actionDown事件中重新标记为false
            isAutoRefresh = true;
            changeState(PtrState.PULL);
            autoScroll.scrollTo(totalDragDistance, 2000);
        }
    };

    public interface OnRefreshListener {
        void reset();

        void onPull();

        void onRefresh();

    }
}
