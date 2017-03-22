package com.boger.game.gc.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boger.game.gc.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 2017/2/13.
 */

public class NineGridVIewGroup extends ViewGroup {

    private List<String> viewData = new ArrayList();

    private Context mContext;

    private onItemClick itemClick;

    private int gap = 4;

    public NineGridVIewGroup(Context context) {
        this(context, null);
    }

    public NineGridVIewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridVIewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        ObjectAnimator.ofInt().start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int row = 0;
    int column = 0;
    int childWidth = 0;
    int childHeight = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        generateChildSize(childCount);
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams lp = childView.getLayoutParams();
            lp.height = childHeight;
            lp.width = childWidth;

            int left;
            int right;
            int top;
            int bottom;
            if (row == 1) {
                left = i * childWidth + i * gap;
                right = i * childWidth + childWidth + i * gap;
                top = childHeight;
                bottom = childHeight + childHeight;
            } else {
                left = (i % column) * childWidth + (i % column) * gap;
                right = (i % column) * childWidth + childWidth + (i % column) * gap;
                top = i / column * childHeight + i / column * gap;
                bottom = i / column * childHeight + childHeight + i / column * gap;
            }
            childView.layout(left, top, right, bottom);
        }
    }

    private void generateChildSize(int childCount) {
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        if (childCount > 0 && childCount <= 3) {
            childWidth = screenWidth / childCount;
            childHeight = childWidth;
            row = 1;
            column = childCount;
        } else if (childCount == 4) {
            childHeight = screenWidth / 2;
            childWidth = childHeight;
            row = 2;
            column = 2;

        } else if (childCount > 4 && childCount <= 6) {
            childHeight = screenWidth / 3;
            childWidth = childHeight;
            row = 2;
            column = 3;
        } else if (childCount > 6 && childCount <= 9) {
            childHeight = screenWidth / 3;
            childWidth = childHeight;
            row = 3;
            column = 3;
        }
    }

    public void setViewData(List<String> lists) {
        if (lists == null || lists.isEmpty()) {
            return;
        }
        this.viewData.clear();
        this.viewData.addAll(lists);
        int i = 0;
        while (i < viewData.size()) {
            ImageView imageView = new ImageView(mContext);
            ImageLoaderUtils.load(lists.get(i), imageView);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onItemClick(viewData.get(finalI));
                }
            });
            addView(imageView, generateDefaultLayoutParams());
            i++;
        }
        requestLayout();
    }


    public void setItemClick(onItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface onItemClick {
        void onItemClick(String url);
    }
}
