package com.boger.game.gc.widget.bottomnav;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.boger.game.gc.utils.PxConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 2016/3/21.
 */
public class BottomNavController extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "BottomNavController";
    private List<BottomNavChild> bottoms = new ArrayList<>();
    private OnChildClickListener listener;
    private int currentPosition = 0;

    public BottomNavController(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
    }

    public BottomNavController(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
    }

    public BottomNavController addItem(BottomNavChild item) {
        bottoms.add(item);
        addView(item);
        return this;
    }

    public BottomNavController setSelectItem(int index) {
        if (index < 0 || index > bottoms.size()) {
            currentPosition = 0;
        } else {
            currentPosition = index;
        }
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getOrientation() == HORIZONTAL) {
            int size = MeasureSpec.getSize(heightMeasureSpec);
            int mode = MeasureSpec.getMode(heightMeasureSpec);

            if (mode == MeasureSpec.AT_MOST) {
                size = PxConvertUtils.dip2px(56, getContext());
            }
            super.onMeasure(widthMeasureSpec,
                    size);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void build(OnChildClickListener listener) {
        this.listener = listener;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (!(view instanceof BottomNavChild)) {
                throw new RuntimeException("widget must extends BottomNavItem");
            }

            BottomNavChild item = (BottomNavChild) view;
            item.setSelect(false);

            item.setTag(i);
            item.setOnClickListener(this);
            bottoms.add(item);

        }
        BottomNavChild item = (BottomNavChild) getChildAt(currentPosition);
        item.setSelect(true);

        listener.onClick(currentPosition);
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

        BottomNavChild inItem = bottoms.get(position);
        BottomNavChild outItem = bottoms.get(currentPosition);

        currentPosition = position;
        inItem.setSelect(true);
        outItem.setSelect(false);
        if (listener != null) {
            listener.onClick(position);
        }
    }

    public interface OnChildClickListener {
        void onClick(int position);
    }
}
