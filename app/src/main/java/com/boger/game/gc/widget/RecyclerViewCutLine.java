package com.boger.game.gc.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/4/5.
 */
public class RecyclerViewCutLine extends RecyclerView.ItemDecoration {
    private int value;
    private int skipPosition;

    public RecyclerViewCutLine(int value, int skipPosition) {
        this.value = value;
        this.skipPosition = skipPosition;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildLayoutPosition(view) != skipPosition) {
            outRect.top = value;
        }
    }


}
