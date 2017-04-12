package com.boger.game.gc.widget.pullToRefreshLayout;

/**
 * Created by liubo on 2017/4/11.
 */

public interface PtrHeader {
    /**
     * 松手
     */
    void reset();

    /**
     * 刚拉出来头部
     */
    void pull();

    /**
     * 拉出来后
     * @param currentPos 当前位置
     * @param lastPos 上一位置
     * @param refreshPos 触发刷新的位置
     * @param isTouch 手指是否按下
     * @param state 当前状态
     */
    void onPulling(float currentPos, float lastPos, float refreshPos, boolean isTouch, PtrState state);
    /**
     * 正在加载
     */
    void loading();

    /**
     * 加载结束
     */
    void complete();
}
