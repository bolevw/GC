package com.example.administrator.gc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.administrator.gc.R;

/**
 * Created by Administrator on 2016/4/28.
 */
public class GridView extends LinearLayout {

    private int column;


    private ViewDragHelper viewDragHelper;

    public GridView(Context context) {
        super(context);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GridView);
        column = ta.getInteger(R.styleable.GridView_column, 3);
        ta.recycle();

    }

    public void setAdapter(BaseAdapter adapter) {
        int itemCount = adapter.getCount();
        int lineHeight = getWidth() / column;
        for (int i = 0; i < itemCount; i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lineHeight));
            linearLayout.setOrientation(HORIZONTAL);
            linearLayout.setWeightSum(column);
            linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));

            linearLayout.setMotionEventSplittingEnabled(false);

            View view = adapter.getView(i, null, null);
            linearLayout.addView(view, new LinearLayout.LayoutParams(lineHeight, lineHeight));

            if (i % column == 0) {
                GridView.this.addView(linearLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallback());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }


    private class ViewDragHelperCallback extends ViewDragHelper.Callback {


        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }
    }

}
