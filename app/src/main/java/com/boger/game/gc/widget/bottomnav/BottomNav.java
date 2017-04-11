package com.boger.game.gc.widget.bottomnav;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 2016/3/21.
 */
public class BottomNav extends LinearLayout implements View.OnClickListener {

    private List<BottomNavItem> bottoms = new ArrayList<>();
    private OnNavItemClickListener listener;
    private int currentPosition = 0;

    public BottomNav(Context context) {
        super(context);
    }

    public BottomNav(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setOrientation(HORIZONTAL);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (!(view instanceof BottomNavItem)) {
                throw new RuntimeException("widget must extends BottomNavItem");
            }

            BottomNavItem item = (BottomNavItem) view;
            item.out();
            item.setTag(i);
            item.setOnClickListener(this);
            bottoms.add(item);

        }
        BottomNavItem item = (BottomNavItem) getChildAt(0);
        item.in();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        selectItem(position);
    }

    public void selectItem(int position) {

        if (position == currentPosition) {
            return;
        }

        BottomNavItem inItem = bottoms.get(position);
        BottomNavItem outItem = bottoms.get(currentPosition);

        currentPosition = position;
        inItem.in();
        outItem.out();
        if (listener != null) {
            listener.onItemClick(position);
        }
    }

    public OnNavItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnNavItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnNavItemClickListener {
        void onItemClick(int position);
    }
}
