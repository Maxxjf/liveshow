package com.qcloud.liveshow.widget.swipeback;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/8/17 15:02.
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    void scrollToFinishActivity();
}
