package com.example.administrator.gc.widget;

/**
 * Created by Administrator on 2016/4/28.
 */
public interface ItemTouchHelperAdapter {
    void itemMove(int fromP, int endP);

    void onItemDisMiss(int position) ;

}
